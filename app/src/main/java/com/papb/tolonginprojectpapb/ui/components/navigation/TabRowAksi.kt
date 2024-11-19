package com.papb.tolonginprojectpapb.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.papb.tolonginprojectpapb.ui.theme.Neutral200
import com.papb.tolonginprojectpapb.ui.theme.Neutral300
import com.papb.tolonginprojectpapb.ui.theme.Primary500

@Composable
fun TabRowAksi(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Primary500)
            .padding(horizontal = 16.dp, vertical = 10.dp),
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(Color.White)
                .padding(0.dp),
//            containerColor = Color.White,
//            indicator = {
//                tabPositions ->
//                Box(
//                    modifier = Modifier
//                        .padding(5.dp)
//                        .offset(tabPositions[selectedTabIndex].left, 0.dp)
//                        .width(56.dp)
//                        .height(48.dp)
//                        .background(Primary500, RoundedCornerShape(50))
//                )
//            }
        ) {
            // Tab Volunteer
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { onTabSelected(0) },
                text = {
                    Text("Volunteer", color = if (selectedTabIndex == 0) Primary500 else Neutral300, fontSize = 16.sp)
                },
                selectedContentColor = Primary500,
                unselectedContentColor = Neutral300,
                modifier = Modifier.clip(RoundedCornerShape(50))
            )

            // Tab Kampanye
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { onTabSelected(1) },
                text = {
                    Text("Kampanye", color = if (selectedTabIndex == 1) Primary500 else Neutral300, fontSize = 16.sp)
                },
                selectedContentColor = Primary500,
                unselectedContentColor = Neutral300,
                modifier = Modifier.clip(RoundedCornerShape(50))
            )
        }
    }
}
