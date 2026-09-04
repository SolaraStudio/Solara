package com.solara.browser.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    data object Browser : Screen("browser")
    data object Home : Screen("home")
    data object Bookmarks : Screen("bookmarks")
    data object History : Screen("history")
    data object Downloads : Screen("downloads")
    data object Settings : Screen("settings")
    data object SplitView : Screen("split_view")
}

data class MenuItem(
    val label: String,
    val icon: ImageVector,
    val screen: Screen
)

val menuItems = listOf(
    MenuItem("Bookmarks", Icons.Default.Bookmark, Screen.Bookmarks),
    MenuItem("History", Icons.Default.History, Screen.History),
    MenuItem("Downloads", Icons.Default.Download, Screen.Downloads),
    MenuItem("Settings", Icons.Default.Settings, Screen.Settings)
)
