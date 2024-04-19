package com.duglasher.composedestinationsstl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.duglasher.composedestinationsstl.ui.theme.ComposeDestinationsSTLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDestinationsSTLTheme {
                STLApp()
            }
        }
    }
}