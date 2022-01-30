package rhea.group.app.ui.screens.session_detail.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import rhea.group.app.models.Session
import rhea.group.app.models.ViewState
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.preferences.PrefsModeImpl
import javax.inject.Inject

@HiltViewModel
class SessionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: ComposeNavigator,
    private val preferences: PrefsModeImpl
) : ViewModel() {
    private val sessionId = savedStateHandle.get<String>(Screen.SessionDetailScreen.navArguments.first().name) ?: ""
    private val _sessionState = MutableStateFlow<ViewState>(ViewState.Loading)
    val sessionState = _sessionState.asStateFlow()

    init {
        viewModelScope.launch {
            _sessionState.value = ViewState.Loading
            val session = preferences.session(sessionId = sessionId).first()
            _sessionState.emit(ViewState.Success(success = session!!))
        }
    }

    fun navigateTo(session: Session) = navigator.navigate(Screen.VideoScreen.createRoute(session.id, false))
    fun navigateToExerciseDetail() = navigator.navigate(Screen.ExerciseDetailScreen.route)
}