package com.example.whiskerpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.example.whiskerpedia.ui.screens.WhiskerpediaApp
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whiskerpedia.ui.screens.ListScreen
import com.example.whiskerpedia.ui.theme.WhiskerpediaTheme
import com.example.whiskerpedia.viewmodel.WhiskerpediaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhiskerpediaTheme (
                //darkTheme = true
            ){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WhiskerpediaApp()
                }
            }
        }
    }
}