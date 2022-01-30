package rhea.group.app.ui.screens.sleep_questionnaire.viewModel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rhea.group.app.models.*
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.preferences.PrefsModeImpl
import rhea.group.app.ui.screens.sleep_questionnaire.repository.SleepQuestionnaireRepository
import rhea.group.app.utils.sleepQuestions
import javax.inject.Inject


@HiltViewModel
class SleepQuestionnaireViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: ComposeNavigator,
    val preferences: PrefsModeImpl,
    private val sleepQuestionnaireRepository: SleepQuestionnaireRepository
) : ViewModel() {
    private val sessionId = savedStateHandle.get<String>(Screen.SleepQuestionnaireScreen.navArguments.first().name) ?: ""
    private val _sleepQuestionnaireViewState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _preferencesViewState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _questionnaireRequest = MutableStateFlow(NetworkState.IDLE)
    private val _sleepQuestionnaireResponse = MutableStateFlow(null as Any?)
    val sleepQuestionnaireResponse = _sleepQuestionnaireResponse.asStateFlow()
    val sleepQuestionnaireViewState = _sleepQuestionnaireViewState.asStateFlow()
    val preferencesViewState = _preferencesViewState.asStateFlow()
    val questionnaireRequest = _questionnaireRequest.asStateFlow()

    lateinit var profile: Profile

    var answers = mutableStateMapOf<String, Boolean?>()

    init {
        viewModelScope.launch {
            profile = preferences.profile.first()!!
            sleepQuestions.map { sq ->
                answers[sq.id] = null
            }
            _preferencesViewState.value = ViewState.Success(success = profile)
            _sleepQuestionnaireViewState.value = ViewState.Success(success = answers)
        }
    }

    fun onClick(isPositive: Boolean, index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _sleepQuestionnaireViewState.value = ViewState.Loading
            answers[sleepQuestions[index].id] = isPositive
            _sleepQuestionnaireViewState.tryEmit(ViewState.Success(success = answers))
        }
    }

    val checkEmptyQuestions: Boolean
        get() {
            return answers.filterValues { a -> a == null }.isNotEmpty()
        }


    fun completeSleepQuestionnaire() {
        viewModelScope.launch {
            _questionnaireRequest.value = NetworkState.LOADING
            sleepQuestionnaireRepository.sleepQuestionnaireCall(
                session = sessionId,
                sleepAnswer = SleepAnswer(answers = answers),
                onStart = { _questionnaireRequest.value = NetworkState.LOADING },
                onError = { _questionnaireRequest.value = NetworkState.ERROR },
                onCompletion = {}
            ).distinctUntilChanged().collect {
                if (it is ErrorResponse) {
                    _sleepQuestionnaireResponse.value = it
                } else {
                    _questionnaireRequest.value = NetworkState.SUCCESS
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