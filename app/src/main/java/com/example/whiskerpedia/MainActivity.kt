package com.example.whiskerpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whiskerpedia.ui.screens.ListScreen
import com.example.whiskerpedia.ui.theme.WhiskerpediaTheme
import com.example.whiskerpedia.viewmodel.WhiskerpediaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhiskerpediaTheme {
                val viewModel: WhiskerpediaViewModel =
                    viewModel(factory = WhiskerpediaViewModel.Factory)

                ListScreen(viewModel)
            }
        }
    }
}