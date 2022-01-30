package rhea.group.app.navigation.unauthenticated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.ui.screens.authentication.AuthenticationScreen
import rhea.group.app.ui.screens.authentication.EmailAuthScreen
import rhea.group.app.ui.screens.breath_question_screen.BreathQuestionScreen
import rhea.group.app.ui.screens.breathwork.BreathworkScreen
import rhea.group.app.ui.screens.breathwork_detail.BreathworkDetailScreen
import rhea.group.app.ui.screens.ending_workout_screen.EndingWorkoutScreen
import rhea.group.app.ui.screens.exercise_detail.ExerciseDetailScreen
import rhea.group.app.ui.screens.holder.TabScreenHolder
import rhea.group.app.ui.screens.session_detail.SessionDetailScreen
import rhea.group.app.ui.screens.sleep_questionnaire.SleepQuestionnaireScreen
import rhea.group.app.ui.screens.symptoms.SymptomsScreen
import rhea.group.app.ui.screens.trial.TrialScreen
import rhea.group.app.ui.screens.video.breath.BreathVideoScreen
import rhea.group.app.ui.screens.video.session.VideoScreen
import rhea.group.app.ui.screens.wearable.WearableScreen

@Composable
fun UnauthenticatedNavigation(navigator: ComposeNavigator) {
    val navController = rememberNavController()
    LaunchedEffect(Unit) { navigator.handleNavigationCommands(navController) }
    NavHost(navController = navController, startDestination = Screen.AuthScreen.route) {
        composable(Screen.AuthScreen.route) { AuthenticationScreen() }
        composable(Screen.EmailAuthScreen.route) { EmailAuthScreen() }
        composable(Screen.TrialScreen.route) { TrialScreen() }
        composable(Screen.WearableScreen.route) { WearableScreen() }
        composable(Screen.EndingWorkoutScreen.route, arguments = Screen.EndingWorkoutScreen.navArguments) { EndingWorkoutScreen() }
        composable(Screen.SymptomsScreen.route, arguments = Screen.SymptomsScreen.navArguments) { SymptomsScreen() }
        composable(Screen.BreathworkScreen.route, arguments = Screen.BreathworkScreen.navArguments) { BreathworkScreen() }
        composable(Screen.TabHolderScreen.route) { TabScreenHolder(navigator = navController) }
        composable(Screen.SessionDetailScreen.route) { SessionDetailScreen() }
        composable(Screen.SleepQuestionnaireScreen.route) { SleepQuestionnaireScreen() }
        composable(Screen.ExerciseDetailScreen.route) { ExerciseDetailScreen() }
        composable(Screen.VideoScreen.route, arguments = Screen.VideoScreen.navArguments) { VideoScreen() }
        composable(Screen.BreathworkDetailScreen.route, arguments = Screen.BreathworkDetailScreen.navArguments) { BreathworkDetailScreen() }
        composable(Screen.BreathQuestionScreen.route, arguments = Screen.BreathQuestionScreen.navArguments) { BreathQuestionScreen() }
        composable(Screen.BreathVideoScreen.route, arguments = Screen.BreathVideoScreen.navArguments) { BreathVideoScreen() }
    }
}