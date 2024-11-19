package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.papb.tolonginprojectpapb.ui.components.headers.NonBackHeader
import com.papb.tolonginprojectpapb.ui.components.navigation.BottomBar
import com.papb.tolonginprojectpapb.ui.components.navigation.TabRowAksi
import com.papb.tolonginprojectpapb.viewmodel.AksiViewModel

@Composable
fun AksiScreen(viewModel: AksiViewModel = viewModel()) {
    // Mengamati LiveData dari ViewModel
    val volunteerList = viewModel.volunteerList.observeAsState(emptyList())
    val isLoading = viewModel.isLoading.observeAsState(true)
    val errorMessage = viewModel.errorMessage.observeAsState(null)

    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NonBackHeader("Aksi")

        TabRowAksi(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading.value -> {
                Text("Loading...", Modifier.padding(16.dp))
            }
            errorMessage.value != null -> {
                Text("Error: ${errorMessage.value}", Modifier.padding(16.dp))
            }
            selectedTabIndex == 0 -> {
                VolunteerScreen(openVolunteerList = volunteerList.value)
            }
            else -> {
                // KampanyeScreen() jika tersedia
            }
        }
    }
}
