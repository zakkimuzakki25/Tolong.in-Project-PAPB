package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.papb.tolonginprojectpapb.entities.CampaignMission
import com.papb.tolonginprojectpapb.ui.components.cards.BigMissionCard
import com.papb.tolonginprojectpapb.ui.components.cards.DailyMissionCard
import com.papb.tolonginprojectpapb.ui.theme.Neutral200
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun KampanyeScreen(
    dailyMissionList: List<CampaignMission>,
    bigMissionList: List<CampaignMission>
) {
    var selectedMissionTab by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        TabRow(
            selectedTabIndex = selectedMissionTab,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Tab(selected = selectedMissionTab == 0, onClick = { selectedMissionTab = 0 }) {
                Text("Misi Harian",
                    style = SetTypography.labelLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = if (selectedMissionTab == 0) Color.Black else Neutral200,
                )
            }
            Tab(selected = selectedMissionTab == 1, onClick = { selectedMissionTab = 1 }) {
                Text("Misi Besar",
                    style = SetTypography.labelLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = if (selectedMissionTab == 1) Color.Black else Neutral200,
                )
            }
        }

        if (selectedMissionTab == 0) {
            Spacer(modifier = Modifier.height(4.dp))
            Text("Rutinitas Harian",
                style = SetTypography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp),
                color = Primary500
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(bottom = 135.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(dailyMissionList.size) { index ->
                    val mission = dailyMissionList[index]
                    DailyMissionCard(
                        id = mission.id,
                        title = mission.title,
                        illustrationUrl = mission.illustration_url,
                        plusXp = mission.plus_xp,
                        category = mission.category
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.height(4.dp))
            Text("Lakukan tantangan berikut ini!",
                style = SetTypography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp),
                color = Primary500
            )
            LazyColumn (
                modifier = Modifier.fillMaxSize()
            ) {
                items(bigMissionList) { mission ->
                    BigMissionCard(
                        id = mission.id,
                        title = mission.title,
                        description = mission.description,
                        illustrationUrl = mission.illustration_url,
                        plusXp = mission.plus_xp,
                        category = mission.category
                    )
                }
            }
        }
    }
}