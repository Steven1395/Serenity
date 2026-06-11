package com.example.serenity.uiux.navigation

sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object Login : Screen("login")
    object SignIn : Screen("signin")
    object Dashboard : Screen("dashboard")
    object Questionnaire : Screen("questionnaire")
    object Journal : Screen("journal")
    object Music : Screen("music")
    object Ai : Screen("ai")
}