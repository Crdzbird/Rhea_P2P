package rhea.group.app.ui.screens.breath_question_screen.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.preferences.PrefsModeImpl
import javax.inject.Inject

@HiltViewModel
class BreathQuestionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: ComposeNavigator,
    val preferences: PrefsModeImpl
) : ViewModel() {
    private val sessionId = savedStateHandle.get<String>(Screen.BreathQuestionScreen.navArguments.first().name)!!
    var time = mutableStateOf(0)

    fun goBack() = navigator.navigateUp()

    fun navigateTo() =
        navigator.navigate(Screen.BreathVideoScreen.createRoute(sessionId, time.value))
}