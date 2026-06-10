package com.example.serenity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.serenity.ui.theme.SerenityTheme

// --- IMPORT SEMUA LAYAR ---
import com.example.serenity.uiux.auth.LandingScreen
import com.example.serenity.uiux.auth.LogInScreen
import com.example.serenity.uiux.auth.SignInScreen
import com.example.serenity.uiux.dashboard.DashboardScreen
import com.example.serenity.uiux.music.MusicScreen
import com.example.serenity.uiux.natunai.AiScreen
import com.example.serenity.uiux.QuestionnaireScreen
import com.example.serenity.uiux.journal.JournalScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SerenityTheme {

                // 💡 Pilihan layar saat ini:
                // "landing", "login", "signin", "questionnaire", "dashboard", "music", "ai"
                var currentScreen by remember { mutableStateOf("landing") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                        when (currentScreen) {
                            "landing" -> {
                                LandingScreen(
                                    onGetStartedClick = { currentScreen = "login" }
                                )
                            }
                            "login" -> {
                                LogInScreen(
                                    onLoginSuccess = { currentScreen = "questionnaire" },
                                    onNavigateToSignIn = { currentScreen = "signin" }
                                )
                            }
                            "signin" -> {
                                SignInScreen(
                                    onContinueClick = { currentScreen = "questionnaire" }
                                )
                            }
                            "questionnaire" -> {
                                QuestionnaireScreen(
                                    onFinished = { currentScreen = "dashboard" }
                                )
                            }
                            "dashboard" -> {
                                DashboardScreen(
                                    onNavigateToMusic = { currentScreen = "music" },
                                    onNavigateToAi = { currentScreen = "ai" },
                                    onNavigateToJournal = { currentScreen = "journal"},
                                    onNavigateToQuestionnaire = { currentScreen = "questionnaire"}
                                )
                            }
                            "music" -> {
                                MusicScreen()
                            }
                            "ai" -> {
                                AiScreen(
                                    onNavigateBack = { currentScreen = "dashboard" }
                                )
                            }
                            "journal" -> {
                                JournalScreen()
                            }
                        }

                    }
                }
            }
        }
    }
}