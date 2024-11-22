package com.papb.tolonginprojectpapb.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.papb.tolonginprojectpapb.ui.components.headers.BackHeader
import com.papb.tolonginprojectpapb.ui.screens.VolunteerDetailScreen
import com.papb.tolonginprojectpapb.viewmodel.VolunteerDetailViewModel

class VolunteerDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val volunteerId = intent.getStringExtra("volunteer_id") ?: ""

        val viewModel: VolunteerDetailViewModel by viewModels {
            VolunteerDetailViewModel.Factory(volunteerId)
        }

        enableEdgeToEdge()
        setContent {
            Scaffold (
                topBar = {
                    BackHeader("Volunteer", {})
                }
            ) { innerPadding ->
                Column (modifier = Modifier.padding(innerPadding)) {
                    VolunteerDetailScreen(viewModel = viewModel)
                }
            }
        }
    }
}
