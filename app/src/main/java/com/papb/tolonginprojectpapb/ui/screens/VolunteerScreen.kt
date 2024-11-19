package com.papb.tolonginprojectpapb.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.papb.tolonginprojectpapb.entities.OpenVolunteer
import com.papb.tolonginprojectpapb.ui.components.cards.OpenVolunteerCard
import com.papb.tolonginprojectpapb.ui.theme.Neutral200
import com.papb.tolonginprojectpapb.ui.theme.Neutral500
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun VolunteerScreen(openVolunteerList: List<OpenVolunteer>) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ){
        val context = LocalContext.current

        Text(
            text = "Volunteer",
            style = SetTypography.labelLarge,
            color = Neutral500,
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(openVolunteerList) { openVolunteer ->
                OpenVolunteerCard(volunteer = openVolunteer)
            }
        }
    }
}