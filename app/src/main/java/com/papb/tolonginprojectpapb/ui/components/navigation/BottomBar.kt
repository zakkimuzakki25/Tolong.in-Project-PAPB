package com.papb.tolonginprojectpapb.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.theme.MaisonNeue
import com.papb.tolonginprojectpapb.ui.theme.Neutral200
import com.papb.tolonginprojectpapb.ui.theme.Primary500

sealed class BottomNavItem(val route: String, val icon: Int, val selectedIcon: Int, val title: String) {
    object Aksi : BottomNavItem("aksi", R.drawable.ic_aksi, R.drawable.ic_aksi_filled, "Aksi")
    object Forum : BottomNavItem("forum", R.drawable.ic_forum, R.drawable.ic_forum_filled, "Forum")
    object Profil : BottomNavItem("profil", R.drawable.ic_profil, R.drawable.ic_profil_filled, "Profil")
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Aksi,
        BottomNavItem.Forum,
        BottomNavItem.Profil
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 12.dp)
            .shadow(16.dp, shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
            .background(Color.White)
    ) {
        BottomAppBar(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                .wrapContentHeight(),
            containerColor = Color.White
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val isSelected = currentRoute == item.route

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        IconButton(
                            onClick = {
                                // Avoid re-navigation to the current route
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        // Keep the back stack clean
                                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            enabled = currentRoute != item.route,
                        ) {
                            Icon(
                                painter = painterResource(id = if (isSelected) item.selectedIcon else item.icon),
                                contentDescription = item.title,
                                tint = if (isSelected) Primary500 else Neutral200,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Text(
                            text = item.title,
                            fontFamily = MaisonNeue,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isSelected) Primary500 else Neutral200,
                            fontSize = 14.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

