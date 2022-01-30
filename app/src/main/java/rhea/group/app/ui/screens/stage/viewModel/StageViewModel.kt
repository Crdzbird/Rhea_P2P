package rhea.group.app.ui.screens.stage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rhea.group.app.models.*
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.preferences.PrefsModeImpl
import rhea.group.app.ui.screens.authentication.repository.ProfileRepository
import rhea.group.app.ui.screens.stage.repository.PlanRepository
import rhea.group.app.ui.screens.stage.repository.SessionRepository
import rhea.group.app.ui.screens.stage.repository.StageRepository
import rhea.group.app.utils.sleepQuestions
import javax.inject.Inject

@HiltViewModel
class StageViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val planRepository: PlanRepository,
    private val stageRepository: StageRepository,
    private val sessionRepository: SessionRepository,
    private val navigator: ComposeNavigator,
    private val preferences: PrefsModeImpl
) : ViewModel() {
    private val _sessions: MutableList<Session> = mutableListOf()
    private val _stageSessions: MutableList<StageSession> = mutableListOf()
    private val _profileState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _planState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _stageState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _sessionState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _isRefreshingState = MutableStateFlow(false)
    val profileState = _profileState.asStateFlow()
    val planState = _planState.asStateFlow()
    val stageState = _stageState.asStateFlow()
    val sessionState = _sessionState.asStateFlow()
    val isRefreshingState = _isRefreshingState.asStateFlow()

    init {
        initializeStage()
    }

    fun onRefresh() {
        initializeStage()
        _isRefreshingState.value = false
    }

    private fun initializeStage() {
        viewModelScope.launch {
            profileRepository.fetchProfile(
                onStart = { _profileState.value = ViewState.Loading },
                onCompletion = {},
                onError = {
                    it?.let { sc ->
                        if (sc.code == 401) {
                            _profileState.value = ViewState.Unauthorized
                        }
                    } ?: run {
                        val profile = preferences.profile.first()
                        profile?.let { p ->
                            _profileState.value = ViewState.Success(success = p)
                            fetchPlan()
                        } ?: run {
                            _profileState.value = ViewState.Error(exception = Exception(""))
                        }
                    }
                }).distinctUntilChanged().collect { result ->
                preferences.updateProfile(result)
                _profileState.value = ViewState.Success(success = result)
                fetchPlan()
            }
        }
    }

    private suspend fun fetchPlan() {
        planRepository.fetchPlan(
            onStart = { _planState.value = ViewState.Loading },
            onCompletion = {},
            onError = {
                it?.let { sc ->
                    if (sc.code == 401) {
                        _planState.value = ViewState.Unauthorized
                    }
                } ?: run {
                    val plan = preferences.plan.first()
                    plan?.let { p ->
                        _planState.value = ViewState.Success(success = p)
                        fetchStage(plan.currentStage)
                    } ?: run {
                        _planState.value = ViewState.Error(exception = Exception(""))
                    }
                }
            }).distinctUntilChanged().collect { result ->
            preferences.updatePlan(result)
            _planState.value = ViewState.Success(success = result)
            fetchStage(result.currentStage)
        }
    }

    private suspend fun fetchStage(stageId: String) {
        stageRepository.fetchStage(
            onStart = { _stageState.value = ViewState.Loading },
            onCompletion = {},
            onError = {
                it?.let { sc ->
                    if (sc.code == 401) {
                        preferences.clearSession()
                        _stageState.value = ViewState.Unauthorized
                    }
                } ?: run {
                    val stage = preferences.stage.first()
                    stage?.let { s ->
                        _stageState.value = ViewState.Success(success = s)
                        buildSessions(s)
                    } ?: run {
                        _stageState.value = ViewState.Error(exception = Exception(""))
                    }
                }
            },
            stage = stageId
        ).distinctUntilChanged().collect { result ->
            preferences.updateStage(result)
            _stageState.value = ViewState.Success(success = result)
            buildSessions(stage = result)
        }
    }

    private suspend fun buildSessions(stage: Stage) {
        _stageSessions.clear()
        _sessions.clear()
        stage.combine().forEachIndexed { index, ss ->
            fetchSession(sessionParam = ss.session!!)
            if (!ss.additionalBreathworkSession.isNullOrEmpty()) {
                fetchBreathSession(ss.additionalBreathworkSession)
            }
            _stageSessions.add(
                StageSession(
                    id = _sessions[index].id,
                    isCompleted = ss.isCompleted,
                    isPending = ss.isPending,
                    additionalBreathworkSession = ss.additionalBreathworkSession,
                    completedAdditionalBreathworkSessions = ss.completedAdditionalBreathworkSessions,
                    isActive = ss.isActive,
                    updateDate = ss.updateDate,
                    feeling = if (_sessions[index].feeling.isEmpty()) null
                    else toEnumFeelings(_sessions[index].feeling),
                    completionDate = _sessions[index].completedTime,
                    category = toEnumCategory(_sessions[index].sessionType),
                    equipments = _sessions[index].equipment,
                    recommendedTime = ss.recommendedTime,
                    session = _sessions[index].name,
                    sleepQuestions = _sessions[index].sleepQuestions
                )
            )
        }
        _sessionState.value = ViewState.Success(success = _stageSessions.toList())
    }

    private suspend fun fetchBreathSession(breathParam: String) {
        sessionRepository.fetchSession(
            onStart = {},
            onCompletion = {},
            onError = {},
            session = breathParam
        ).distinctUntilChanged().collect { result ->
            preferences.updateSession(result)
        }
    }

    private suspend fun fetchSession(sessionParam: String) {
        sessionRepository.fetchSession(
            onStart = { _sessionState.value = ViewState.Loading },
            onCompletion = {},
            onError = {
                it?.let { sc ->
                    if (sc.code == 401) {
                        _sessionState.value = ViewState.Unauthorized
                    }
                } ?: run {
                    val session = preferences.session(sessionId = sessionParam).first()
                    session?.let { s ->
                        _sessions.add(s)
                    }
                }
            },
            session = sessionParam
        ).distinctUntilChanged().collect { result ->
            preferences.updateSession(result)
            _sessions.add(result)
        }
    }

    fun navigateAndSetQuestion(it: List<SleepQuestion>, stageSession: StageSession) {
        sleepQuestions = it
        navigator.navigate(Screen.SleepQuestionnaireScreen.createRoute(stageSession.id))
    }

    fun navigateToSessionDetail(stageSession: StageSession) = navigator.navigate(Screen.SessionDetailScreen.createRoute(stageSession.id))
    fun navigateToBreathworkScreen(additionalBreathworkSession: String) = navigator.navigate(Screen.BreathworkScreen.createRoute(additionalBreathworkSession))
}