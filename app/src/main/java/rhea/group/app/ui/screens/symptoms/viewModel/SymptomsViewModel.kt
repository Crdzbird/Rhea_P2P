package rhea.group.app.ui.screens.symptoms.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import rhea.group.app.models.BreathworkComplete
import rhea.group.app.models.Feeling
import rhea.group.app.models.FeelingsType
import rhea.group.app.models.ViewState
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.ui.screens.symptoms.repository.BreathworkRepository
import rhea.group.app.ui.screens.symptoms.repository.SymptomsRepository
import javax.inject.Inject

@HiltViewModel
class SymptomsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val symptomsRepository: SymptomsRepository,
    private val breathworkRepository: BreathworkRepository,
    private val navigator: ComposeNavigator
) : ViewModel() {
    val selectedOptionReason = mutableStateOf("")
    private val sessionId = savedStateHandle.get<String>(Screen.SymptomsScreen.navArguments[0].name)!!
    private val duration = Integer.parseInt(savedStateHandle.get<String>(Screen.SymptomsScreen.navArguments[1].name)!!)
    private val breathwork_type = savedStateHandle.get<String>(Screen.SymptomsScreen.navArguments[2].name)

    private val _sessionState = MutableStateFlow<ViewState>(ViewState.Loading)

    fun completeWorkout() {
        viewModelScope.launch {
            breathwork_type?.let { bt ->
                breathworkRepository.breathworkCall(
                    session = sessionId,
                    breathworkComplete = BreathworkComplete(
                        feeling = Feeling(FeelingsType.valueOf(selectedOptionReason.value)).feeling,
                        breathwork_type = bt,
                        total_duration = duration
                    ),
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
            } ?: kotlin.run {
                symptomsRepository.symptomsCall(
                    session = sessionId,
                    feeling = Feeling(FeelingsType.valueOf(selectedOptionReason.value)),
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
}