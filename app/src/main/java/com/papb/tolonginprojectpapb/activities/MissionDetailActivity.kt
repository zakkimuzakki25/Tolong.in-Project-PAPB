package com.papb.tolonginprojectpapb.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.papb.tolonginprojectpapb.ui.components.headers.BackHeader
import com.papb.tolonginprojectpapb.ui.screens.BigMissionDetailScreen
import com.papb.tolonginprojectpapb.ui.screens.DailyMissionDetailScreen
import com.papb.tolonginprojectpapb.viewmodel.MissionViewModel

class MissionDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val missionId = intent?.getStringExtra("MISSION_ID") ?: ""
        println("Received mission ID: $missionId")

        val missionViewModel: MissionViewModel = ViewModelProvider(this)[MissionViewModel::class.java]

        missionViewModel.fetchMissionById(missionId)

        setContent {
            val mission by missionViewModel.missionDetail.observeAsState()

            Scaffold(
                topBar = {
                    if (mission != null) {
                        BackHeader(
                            title = mission!!.title
                        )
                    }
                }
            ) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    when {
                        mission == null -> {
                            Text(text = "Loading...")
                        }
                        !mission!!.is_big -> {
                            DailyMissionDetailScreen(mission = mission!!)
                        }
                        mission!!.is_big -> {
                            BigMissionDetailScreen(mission = mission!!)
                        }
                        else -> {
                            Text(text = "Mission details not available.")
                        }
                    }
                }
            }
        }
    }
}
