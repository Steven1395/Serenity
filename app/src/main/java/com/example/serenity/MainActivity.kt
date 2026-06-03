package com.example.serenity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.serenity.ui.theme.NaptuneTheme
import com.example.serenity.uiux.QuestionnaireScreen
import com.example.serenity.uiux.auth.LandingScreen
import com.example.serenity.uiux.auth.LogInScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NaptuneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                        // State pengatur jalan cerita layar (Dimulai dari "landing")
                        var currentScreen by remember { mutableStateOf("landing") }

                        // Logika perpindahan halamannya di sini
                        when (currentScreen) {
                            "landing" -> LandingScreen(
                                onGetStartedClick = { currentScreen = "login" }
                            )
                            "login" -> LogInScreen(
                                onLoginSuccess = { currentScreen = "questionnaire" }
                            )
                            "questionnaire" -> QuestionnaireScreen()
                        }

                    }
                }
            }
        }
    }
}