package com.papb.tolonginprojectpapb.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.papb.tolonginprojectpapb.activities.LogInActivity
import com.papb.tolonginprojectpapb.activities.SignUpActivity

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val auth = FirebaseAuth.getInstance()

    NavHost(navController = navController, startDestination = if (auth.currentUser != null) "login" else "login", modifier = modifier) {

        composable("login") {
            LogInActivity(
//                auth = auth,
//                onLoginSuccess = {
//                    navController.navigate("volunteer") {
//                        popUpTo("login") { inclusive = true }
//                    }
//                }
            )
        }

        composable("signup") {
            SignUpActivity(
//                auth = auth,
//                onLoginSuccess = {
//                    navController.navigate("volunteer") {
//                        popUpTo("login") { inclusive = true }
//                    }
//                }
            )
        }

        composable("volunteer") {
//            ScheduleScreen(navController)
            Text("hello")
        }

//        composable("profile") {
//            val viewModel = viewModel<GithubProfileViewModel>()
//            GithubProfileScreen(viewModel, "zakkimuzakki25")
//        }
//
//        composable("tugas") {
//            val tugasViewModel = viewModel<TugasViewModel>()
//            TugasScreen(viewModel = tugasViewModel)
//        }

    }
}