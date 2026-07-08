package com.example.serenity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.serenity.ui.theme.SerenityTheme
import com.example.serenity.uiux.navigation.SerenityApp
import com.example.serenity.uiux.navigation.Screen // WAJIB DI-IMPORT AGAR BISA MEMBACA Screen.Landing.route

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SerenityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                        // Biarkan aplikasi selalu mulai dari Landing Screen secara default.
                        // Pengecekan Kuesioner/Dashboard akan otomatis berjalan SETELAH user berhasil Login.
                        SerenityApp(startDestination = Screen.Landing.route)

                    }
                }
            }
        }
    }
}