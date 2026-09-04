package com.solara.browser.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.solara.browser.ui.layouts.BrowserScreen
import com.solara.browser.ui.screens.BookmarksScreen
import com.solara.browser.ui.screens.DownloadsScreen
import com.solara.browser.ui.screens.HistoryScreen
import com.solara.browser.ui.screens.SettingsScreen
import com.solara.browser.ui.viewmodel.BrowserViewModel
import com.solara.browser.ui.viewmodel.SettingsViewModel

@Composable
fun SolaraNavGraph(
    navController: NavHostController,
    browserViewModel: BrowserViewModel,
    settingsViewModel: SettingsViewModel,
    initialUrl: String
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Browser.route
    ) {
        composable(Screen.Browser.route) {
            BrowserScreen(
                initialUrl = initialUrl,
                viewModel = browserViewModel
            )
        }

        composable(Screen.Bookmarks.route) {
            BookmarksScreen(
                viewModel = browserViewModel,
                onBack = { navController.popBackStack() },
                onBookmarkClick = { url ->
                    browserViewModel.navigateTo(url)
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                viewModel = browserViewModel,
                onBack = { navController.popBackStack() },
                onEntryClick = { url ->
                    browserViewModel.navigateTo(url)
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Downloads.route) {
            DownloadsScreen(
                viewModel = browserViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = settingsViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
