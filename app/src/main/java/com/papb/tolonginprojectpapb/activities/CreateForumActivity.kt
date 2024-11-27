package com.papb.tolonginprojectpapb.activities

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.papb.tolonginprojectpapb.ui.screens.CreateForumScreen
import com.papb.tolonginprojectpapb.ui.theme.TolonginProjectPAPBTheme
import com.papb.tolonginprojectpapb.ui.viewmodel.CreateForumViewModel

class CreateForumActivity : ComponentActivity() {

    private val viewModel: CreateForumViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TolonginProjectPAPBTheme {
                CreateForumScreen(
                    viewModel = viewModel,
                    onForumCreated = {
                        setResult(RESULT_OK)
                        finish()
                    }
                )
            }
        }
    }
}
