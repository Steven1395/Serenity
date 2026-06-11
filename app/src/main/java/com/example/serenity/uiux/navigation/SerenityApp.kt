package com.example.serenity.uiux.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Import semua layar
import com.example.serenity.uiux.auth.LandingScreen
import com.example.serenity.uiux.auth.LogInScreen
import com.example.serenity.uiux.auth.SignInScreen
import com.example.serenity.uiux.dashboard.DashboardScreen
import com.example.serenity.uiux.questionnaire.QuestionnaireScreen
import com.example.serenity.uiux.journal.JournalScreen
import com.example.serenity.uiux.music.MusicScreen
import com.example.serenity.uiux.natunai.AiScreen

@Composable
fun SerenityApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Landing.route) {

        composable(Screen.Landing.route) {
            LandingScreen(onGetStartedClick = { navController.navigate(Screen.Login.route) })
        }

        composable(Screen.Login.route) {
            LogInScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Questionnaire.route) {
                        // popUpTo ini menghapus history Login biar user gabisa back ke Login lagi
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignIn = { navController.navigate(Screen.SignIn.route) }
            )
        }

        composable(Screen.SignIn.route) {
            SignInScreen(onContinueClick = { navController.navigate(Screen.Questionnaire.route) })
        }

        composable(Screen.Questionnaire.route) {
            QuestionnaireScreen(onFinished = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Questionnaire.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToMusic = { navController.navigate(Screen.Music.route) },
                onNavigateToAi = { navController.navigate(Screen.Ai.route) },
                onNavigateToJournal = { navController.navigate(Screen.Journal.route) },
                onNavigateToQuestionnaire = { navController.navigate(Screen.Questionnaire.route) }
            )
        }

        // Pakai popBackStack() untuk tombol kembali yang natural
        composable(Screen.Music.route) {
            MusicScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(Screen.Ai.route) {
            AiScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(Screen.Journal.route) {
            JournalScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}