package rhea.group.app.ui.screens.breathwork_detail.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import javax.inject.Inject

@HiltViewModel
class BreathworkDetailViewModel  @Inject constructor(
    private val navigator: ComposeNavigator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val sessionId = savedStateHandle.get<String>(Screen.BreathworkDetailScreen.navArguments.first().name)!!

    fun navigateTo() = navigator.navigate(Screen.BreathQuestionScreen.createRoute(sessionId))
}