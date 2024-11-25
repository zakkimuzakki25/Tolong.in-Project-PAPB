package com.papb.tolonginprojectpapb.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.papb.tolonginprojectpapb.ui.screens.CreateForumScreen
import com.papb.tolonginprojectpapb.viewmodel.CreateForumViewModel
import com.papb.tolonginprojectpapb.ui.theme.TolonginProjectPAPBTheme

class CreateForumActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[CreateForumViewModel::class.java]

        setContent {
            TolonginProjectPAPBTheme {
                CreateForumScreen(
                    viewModel = viewModel,
                    onForumCreated = { finish() }
                )
            }
        }
    }
}
