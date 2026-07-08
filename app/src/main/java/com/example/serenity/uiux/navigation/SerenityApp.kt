package com.example.serenity.uiux.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Import semua layar
import com.example.serenity.uiux.auth.LandingScreen
import com.example.serenity.uiux.auth.LogInScreen
import com.example.serenity.uiux.auth.SignInScreen
import com.example.serenity.uiux.auth.ForgotPasswordScreen
import com.example.serenity.uiux.dashboard.DashboardScreen
import com.example.serenity.uiux.questionnaire.QuestionnaireScreen
import com.example.serenity.uiux.journal.JournalScreen
import com.example.serenity.uiux.music.MusicScreen
import com.example.serenity.uiux.natunai.AiScreen
import com.example.serenity.utils.DailyChecker // 1. IMPORT DAILYCHECKER KAMU

@Composable
fun SerenityApp(
    // PERBAIKAN A: Tambahkan parameter startDestination agar fleksibel dikontrol dari MainActivity
    // Nilai default-nya tetap Landing jika tidak dikirim dari MainActivity
    startDestination: String = Screen.Landing.route
) {
    val navController = rememberNavController()

    // Siapkan dailyChecker untuk mengecek status pengisian kuesioner hari ini
    val context = LocalContext.current
    val dailyChecker = remember { DailyChecker(context) }

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Screen.Landing.route) {
            LandingScreen(onGetStartedClick = { navController.navigate(Screen.Login.route) })
        }

        composable(Screen.Login.route) {
            LogInScreen(
                onLoginSuccess = {
                    // PERBAIKAN B: Cegat alur setelah login sukses menggunakan DailyChecker
                    val ruteTujuan = if (dailyChecker.isAlreadyFilledToday()) {
                        Screen.Dashboard.route // Jika sudah isi hari ini, langsung ke Dashboard
                    } else {
                        Screen.Questionnaire.route // Jika belum isi, wajib kuesioner dulu
                    }

                    navController.navigate(ruteTujuan) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignIn = { navController.navigate(Screen.SignIn.route) },
                onNavigateToForgotPass = { navController.navigate(Screen.ForgotPassword.route) }
            )
        }

        // --- 1. PERBAIKAN ALUR SIGN IN ---
        composable(Screen.SignIn.route) {
            SignInScreen(
                onContinueClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                }
            )
        }

        // --- 2. PERBAIKAN FORGOT PASSWORD (TANPA PARAMETER) ---
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(onNavigateBackToLogin = {
                navController.popBackStack()
            })
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
                onNavigateToQuestionnaire = { navController.navigate(Screen.Questionnaire.route) } // Tetap bisa diakses manual jika user ingin mengulang
            )
        }

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