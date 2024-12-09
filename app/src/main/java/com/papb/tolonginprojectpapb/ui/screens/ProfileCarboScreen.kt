package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.viewmodel.ProfileViewModel

@Composable
fun ProfileCarboScreen(viewModel: ProfileViewModel) {
    val carbonData by viewModel.carbonData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_leaf),
            contentDescription = "Leaf Icon",
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Kamu menghemat total ${carbonData?.first} kg karbon",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            textAlign = TextAlign.Center
        )
    }
}
