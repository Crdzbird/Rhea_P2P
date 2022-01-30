package rhea.group.app.ui.screens.stage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rhea.group.app.models.ViewState
import rhea.group.app.preferences.PrefsModeImpl
import rhea.group.app.ui.screens.stage.repository.SessionRepository
import javax.inject.Inject

@HiltViewModel
class SessionCardViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val preferences: PrefsModeImpl
) : ViewModel() {
    private val _sessionState = MutableStateFlow<ViewState>(ViewState.Loading)
    val sessionState = _sessionState.asStateFlow()

    suspend fun fetchSession(sessionParam: String) {
        viewModelScope.launch {
            sessionRepository.fetchSession(
                onStart = { _sessionState.value = ViewState.Loading },
                onCompletion = {},
                onError = {
                    val session = preferences.session(sessionId = sessionParam).first()
                    session?.let { s ->
                        _sessionState.value = ViewState.Success(success = s)
                    } ?: run {
                        _sessionState.value = ViewState.Error(exception = Throwable(""))
                    }
                },
                session = sessionParam
            ).distinctUntilChanged().collect { result ->
                _sessionState.value = ViewState.Success(success = result)
            }
        }
    }
}