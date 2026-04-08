package com.athallah.laundryku.navigation

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object About : Screen("about")
}
