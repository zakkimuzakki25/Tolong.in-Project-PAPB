package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.papb.tolonginprojectpapb.ui.components.headers.NonBackHeader
import com.papb.tolonginprojectpapb.ui.components.navigation.TabRowAksi
import com.papb.tolonginprojectpapb.viewmodel.AksiViewModel

@Composable
fun AksiScreen(viewModel: AksiViewModel = viewModel()) {
    val volunteerList = viewModel.volunteerList.observeAsState(emptyList())
    val isLoading = viewModel.isLoading.observeAsState(true)
    val errorMessage = viewModel.errorMessage.observeAsState(null)

    val dailyMissionList = viewModel.dailyMissionList.observeAsState(emptyList())
    val bigMissionList = viewModel.bigMissionList.observeAsState(emptyList())

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
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Loading...", Modifier.padding(16.dp))
                }
            }
            errorMessage.value != null -> {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error: ${errorMessage.value}", Modifier.padding(16.dp))
                }
            }
            selectedTabIndex == 0 -> {
                VolunteerScreen(openVolunteerList = volunteerList.value)
            }
            else -> {
                 KampanyeScreen(
                     dailyMissionList = dailyMissionList.value,
                     bigMissionList = bigMissionList.value,
                 )
            }
        }
    }
}
