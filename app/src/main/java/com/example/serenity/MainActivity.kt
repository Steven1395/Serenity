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
import com.example.serenity.uiux.auth.SignInScreen // Tambahan Import

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NaptuneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                        var currentScreen by remember { mutableStateOf("landing") }

                        // Alur Navigasi Lengkap
                        when (currentScreen) {
                            "landing" -> LandingScreen(
                                onGetStartedClick = { currentScreen = "login" }
                            )
                            "login" -> LogInScreen(
                                onLoginSuccess = { currentScreen = "questionnaire" },
                                onNavigateToSignIn = { currentScreen = "signin" } // Navigasi ke Sign In
                            )
                            "signin" -> SignInScreen(
                                onContinueClick = { currentScreen = "questionnaire" }
                            )
                            "questionnaire" -> QuestionnaireScreen()
                        }

                    }
                }
            }
        }
    }
}