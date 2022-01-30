package rhea.group.app.navigator

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    private val baseRoute: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    val route: String = baseRoute.appendArguments(navArguments)

    object AuthScreen : Screen("auth_screen")
    object EmailAuthScreen : Screen("email_auth_screen")
    object TrialScreen : Screen("trial_screen")
    object WearableScreen : Screen("wearable_screen")
    object TabHolderScreen : Screen("tab_holder_screen")
    object SessionDetailScreen : Screen(baseRoute = "session_detail_screen", navArguments = listOf(navArgument("session") { type = NavType.StringType})){
        fun createRoute(session: String) =
            route.replace("{${navArguments.first().name}}", session)
    }
    object SleepQuestionnaireScreen : Screen(baseRoute = "sleep_questionnaire_screen", navArguments = listOf(navArgument("session") { type = NavType.StringType })){
        fun createRoute(session: String) =
            route.replace("{${navArguments.first().name}}", session)
    }
    object BreathworkScreen : Screen(baseRoute = "breathwork_screen", navArguments = listOf(navArgument("session") { type = NavType.StringType})){
        fun createRoute(session: String) =
            route.replace("{${navArguments.first().name}}", session)
    }
    object ExerciseDetailScreen : Screen("exercise_detail_screen")
    object EndingWorkoutScreen : Screen(baseRoute = "ending_workout_screen", navArguments = listOf(navArgument("session") { type = NavType.StringType})){
        fun createRoute(session: String) =
            route.replace("{${navArguments.first().name}}", session)
    }
    object SymptomsScreen : Screen(baseRoute = "symptom_screen", navArguments = listOf(
            navArgument("session") {
                type = NavType.StringType },
            navArgument("breathwork_type") {
                type = NavType.StringType
                nullable = true },
            navArgument("duration") {
                type = NavType.StringType
                nullable = true
            }
        )
    ){
        fun createRoute(session: String, breathwork_type: String?, duration: Int?): String {
            route.replace("{${navArguments[0].name}}", session)
            breathwork_type?.let {
                route.replace("{${navArguments[1].name}}", it)
            }
            duration?.let {
                route.replace("{${navArguments[2].name}}", "$it")
            }
            return route
        }
    }
    object VideoScreen : Screen(baseRoute = "video_screen", navArguments = listOf(
        navArgument("session") {
            type = NavType.StringType
        },
        navArgument("isPreview") {
            type = NavType.BoolType
        }
    )
    ){
        fun createRoute(session: String, isPreview: Boolean?): String {
            route.replace("{${navArguments[0].name}}", session)
            isPreview?.let {
                route.replace("{${navArguments[1].name}}", it.toString())
            }
            return route
        }
    }
    object BreathQuestionScreen : Screen(baseRoute = "breath_question_screen", navArguments = listOf(navArgument("session") { type = NavType.StringType})){
        fun createRoute(session: String) =
            route.replace("{${navArguments.first().name}}", session)
    }
    object BreathworkDetailScreen : Screen(baseRoute = "breath_detail_screen", navArguments = listOf(navArgument("session") { type = NavType.StringType})){
        fun createRoute(session: String) =
            route.replace("{${navArguments.first().name}}", session)
    }

    object BreathVideoScreen : Screen(baseRoute = "breath_video_screen", navArguments = listOf(
        navArgument("session") { type = NavType.StringType },
        navArgument("time") { type = NavType.IntType }
    )) {
        fun createRoute(session: String, time: Int): String {
            route.replace("{${navArguments[0].name}}", session)
            time.let {
                route.replace("{${navArguments[1].name}}", "$it")
            }
            return route
        }
    }
    object StageTabScreen : Screen("Stage")
    object SettingsTabScreen : Screen("Settings")
}

private fun String.appendArguments(navArguments: List<NamedNavArgument>): String {
    val mandatoryArguments = navArguments.filter { it.argument.defaultValue == null }
        .takeIf { it.isNotEmpty() }
        ?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }
        .orEmpty()
    val optionalArguments = navArguments.filter { it.argument.defaultValue != null }
        .takeIf { it.isNotEmpty() }
        ?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }
        .orEmpty()
    return "$this$mandatoryArguments$optionalArguments"
}