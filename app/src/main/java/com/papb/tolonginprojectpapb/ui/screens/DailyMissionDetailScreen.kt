package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.entities.CampaignMission
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.cards.StatisticCard
import com.papb.tolonginprojectpapb.ui.components.popups.PopUpAddMission
import com.papb.tolonginprojectpapb.ui.components.popups.PopUpMissionSucces
import com.papb.tolonginprojectpapb.ui.theme.Based200
import com.papb.tolonginprojectpapb.ui.theme.Neutral500
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import com.papb.tolonginprojectpapb.ui.theme.Secondary500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun DailyMissionDetailScreen(
    mission: CampaignMission,
) {
    var showIsiDokumentasiPopup by remember { mutableStateOf(false) }
    var isMissionCompleted by remember { mutableStateOf(false) }
    var isMissionSuccse by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    val firestore = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    LaunchedEffect(mission.id, userId) {
        firestore.collection("users_missions")
            .whereEqualTo("user_id", userId)
            .whereEqualTo("mission_id", mission.id)
            .get()
            .addOnSuccessListener { documents ->
                isMissionCompleted = !documents.isEmpty
                isLoading = false
            }
            .addOnFailureListener {
                println("Gagal memeriksa relasi: ${it.message}")
                isLoading = false
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Based200)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = mission.illustration_url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .offset(y = 44.dp)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = mission.category,
                        modifier = Modifier
                            .background(
                                color = Secondary500,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 24.dp, vertical = 4.dp),
                        color = Neutral500,
                        style = SetTypography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = "Star Icon",
                            tint = Secondary500,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "+${mission.plus_xp} XP",
                            style = SetTypography.bodyMedium,
                            color = Neutral500
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = mission.subtitle,
                    style = SetTypography.labelLarge,
                    color = Primary500,
                    modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
                )

                Text(
                    text = mission.description,
                    style = SetTypography.bodyMedium,
                    color = Neutral500,
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                StatisticCard("0.1", "CO2 Terselamatkan", Modifier.weight(1f), id_icon = R.drawable.ic_co2)
                Spacer(modifier = Modifier.width(14.dp))
                StatisticCard("0.0", "Air Terselamatkan", Modifier.weight(1f), id_icon = R.drawable.ic_air)
                Spacer(modifier = Modifier.width(14.dp))
                StatisticCard("0.0", "Listrik Terselamatkan", Modifier.weight(1f), id_icon = R.drawable.ic_listrik)
            }
        }

        PrimerButton(
            text = if (isMissionCompleted) "Selesai" else "Lakukan",
            isActive = !isMissionCompleted && !isLoading,
            handle = {
                if (!isMissionCompleted) {
                    showIsiDokumentasiPopup = true
                }
            },
            size = ButtonSize.LARGE,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }

    if (showIsiDokumentasiPopup) {
        PopUpAddMission(
            missionId = mission.id,
            userId = userId,
            onDismiss = { showIsiDokumentasiPopup = false },
            onConfirm = {
                isMissionCompleted = true
                showIsiDokumentasiPopup = false
                isMissionSuccse = true
            }
        )
    }

    if (isMissionSuccse) {
        PopUpMissionSucces(
            onDismiss = { isMissionSuccse = false },
            title = mission.title,
            xp = mission.plus_xp
        )
    }
}