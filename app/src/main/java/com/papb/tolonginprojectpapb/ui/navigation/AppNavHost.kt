package com.papb.tolonginprojectpapb.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.papb.tolonginprojectpapb.ui.screens.AksiScreen
import com.papb.tolonginprojectpapb.ui.screens.ForumScreen
import com.papb.tolonginprojectpapb.ui.screens.ProfileScreen
import com.papb.tolonginprojectpapb.viewmodel.AksiViewModel
import com.papb.tolonginprojectpapb.viewmodel.ForumViewModel
import com.papb.tolonginprojectpapb.viewmodel.ProfileViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "aksi", modifier = modifier) {

        composable("aksi") {
            val viewModel = viewModel<AksiViewModel>()
            AksiScreen(viewModel)
        }

        composable("forum") {
            val viewModel = viewModel<ForumViewModel>()
            ForumScreen(viewModel)
        }

        composable("profil") {
            val viewModel = viewModel<ProfileViewModel>()
            ProfileScreen(viewModel)
        }
    }
}