package com.papb.tolonginprojectpapb.ui.screens

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.activities.LogInActivity
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography
import com.papb.tolonginprojectpapb.viewmodel.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main_profile"
    ) {
        composable("main_profile") {
            MainProfileScreen(navController, viewModel)
        }
        composable("settings") {
            val context = LocalContext.current

            ProfileSettingScreen(
                onEditProfileClick = { navController.navigate("edit_profile") },
                onConfirmSignOut = {
                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(context, LogInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }
            )
        }

        composable("edit_profile") {
            ProfileEditScreen(
                onSaveClick = { name, phone, email ->
                    viewModel.updateProfile(name, phone, email)
                    navController.popBackStack()
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainProfileScreen(navController: NavHostController, viewModel: ProfileViewModel) {
    val user by viewModel.user.observeAsState()
    val tabs = listOf("Unggahan", "Total Karbon")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Profil Pengguna",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            actions = {
                IconButton(onClick = { navController.navigate("settings") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = "Settings Icon",
                        modifier = Modifier.size(25.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(12.dp))
                AsyncImage(
                    model = user?.avatar_url,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(72.dp)
                        .background(Primary500, RoundedCornerShape(50)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column (
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ){
                    Text(
                        text = user?.fullname ?: "Nama tidak ditemukan",
                        style = SetTypography.bodyLarge
                    )
                    Text(
                        text = user?.let { "@${it.username}" } ?: "@username",
                        style = SetTypography.bodySmall,
                        color = Color.Black
                    )
                    Text(
                        text = user?.let { "🌟 ${it.xp} XP" } ?: "🌟 0 XP",
                        style = SetTypography.bodySmall,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.background(Color.White),
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTabIndex) {
                0 -> ProfilePostsScreen(viewModel)
                1 -> ProfileCarboScreen(viewModel)
            }
        }
    }
}
