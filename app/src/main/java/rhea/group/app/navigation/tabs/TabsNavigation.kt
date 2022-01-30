package rhea.group.app.navigation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.ui.screens.settings.SettingsScreen
import rhea.group.app.ui.screens.stage.StageTabScreen

@Composable
fun TabsNavigation(navigator: ComposeNavigator) {
    val navController = rememberNavController()
    LaunchedEffect(Unit) { navigator.handleNavigationCommands(navController) }
    NavHost(navController, startDestination = Screen.StageTabScreen.route) {
        composable(Screen.StageTabScreen.route) { StageTabScreen() }
        composable(Screen.SettingsTabScreen.route) { SettingsScreen() }
    }
}

//@Composable
//fun TabsNavigation(navController: NavHostController, navHostController: NavHostController) {
//    NavHost(navController, startDestination = Screen.StageTabScreen.route) {
//        composable(Screen.StageTabScreen.route) {
//            StageTabScreen(
//                navHostController = navHostController
//            )
//        }
//        composable(Screen.SettingsTabScreen.route) { SettingsScreen() }
//    }
//}