package rhea.group.app.navigation.freemium

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.ui.screens.trial.TrialScreen

@Composable
fun FreemiumNavigation(navigator: ComposeNavigator) {
    val navController = rememberNavController()
    LaunchedEffect(Unit) { navigator.handleNavigationCommands(navController) }
    NavHost(navController = navController, startDestination = Screen.TrialScreen.route) {
        composable(Screen.TrialScreen.route) { TrialScreen() }
    }
}