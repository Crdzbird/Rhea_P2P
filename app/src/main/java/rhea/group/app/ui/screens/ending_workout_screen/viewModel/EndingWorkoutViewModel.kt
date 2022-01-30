package rhea.group.app.ui.screens.ending_workout_screen.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import rhea.group.app.models.Reason
import rhea.group.app.models.ViewState
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.ui.screens.ending_workout_screen.repository.EndingWorkoutRepository
import javax.inject.Inject

@HiltViewModel
class EndingWorkoutViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val endingWorkoutRepository: EndingWorkoutRepository,
    private val navigator: ComposeNavigator
) : ViewModel() {
    private val sessionId = savedStateHandle.get<String>(Screen.EndingWorkoutScreen.navArguments.first().name) ?: ""
    private val _sessionState = MutableStateFlow<ViewState>(ViewState.Loading)
    fun endWorkout(reason: Reason) {
        viewModelScope.launch {
            endingWorkoutRepository.endWorkout(
                session = sessionId,
                reason = reason,
                onStart = { _sessionState.value = ViewState.Loading },
                onError = {
                    it?.let { sc ->
                        if (sc.code == 401) {
                            _sessionState.value = ViewState.Unauthorized
                        }
                    } ?: run {
                        _sessionState.value = ViewState.Unauthorized
                    }
                },
                onCompletion = {}
            ).distinctUntilChanged().collect {
                navigator.popUpTo(
                    Screen.TabHolderScreen.route,
                    inclusive = true
                )
                navigator.navigate(Screen.TabHolderScreen.route)
            }
        }
    }
}