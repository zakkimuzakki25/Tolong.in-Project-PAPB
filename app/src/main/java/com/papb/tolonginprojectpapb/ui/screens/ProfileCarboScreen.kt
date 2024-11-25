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

    var selectedMonth by remember { mutableStateOf("September") }
    var selectedYear by remember { mutableStateOf("2024") }
    val months = listOf(
        "Januari", "Februari", "Maret", "April", "Mei", "Juni",
        "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    )
    val years = listOf("2022", "2023", "2024", "2025", "2026")

    var expandedMonth by remember { mutableStateOf(false) }
    var expandedYear by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // Dropdown for Months
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Bulan", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { expandedMonth = true },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.width(140.dp)
                ) {
                    Text(text = selectedMonth)
                }
                DropdownMenu(
                    expanded = expandedMonth,
                    onDismissRequest = { expandedMonth = false }
                ) {
                    months.forEach { month ->
                        DropdownMenuItem(
                            text = { Text(month) },
                            onClick = {
                                selectedMonth = month
                                expandedMonth = false
                            }
                        )
                    }
                }
            }

            // Dropdown for Years
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Tahun", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { expandedYear = true },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.width(140.dp)
                ) {
                    Text(text = selectedYear)
                }
                DropdownMenu(
                    expanded = expandedYear,
                    onDismissRequest = { expandedYear = false }
                ) {
                    years.forEach { year ->
                        DropdownMenuItem(
                            text = { Text(year) },
                            onClick = {
                                selectedYear = year
                                expandedYear = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Carbon Data Display
        Image(
            painter = painterResource(id = R.drawable.ic_leaf),
            contentDescription = "Leaf Icon",
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Kamu menghasilkan ${carbonData?.first} kg",
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
            textAlign = TextAlign.Center
        )

        Text(
            text = "/ ${carbonData?.second} kg CO2 bulan ini",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            textAlign = TextAlign.Center
        )
    }
}
