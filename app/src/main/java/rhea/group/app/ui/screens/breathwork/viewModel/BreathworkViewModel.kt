package rhea.group.app.ui.screens.breathwork.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import rhea.group.app.models.ViewState
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.preferences.PrefsModeImpl
import javax.inject.Inject

@HiltViewModel
class BreathworkViewModel @Inject constructor(
    private val preferences: PrefsModeImpl,
    private val navigator: ComposeNavigator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val sessionId = savedStateHandle.get<String>(Screen.BreathworkScreen.navArguments.first().name)!!
    private val _breathworkState = MutableStateFlow<ViewState>(ViewState.Loading)
    val breathworkState = _breathworkState.asStateFlow()

    init {
        viewModelScope.launch {
            _breathworkState.value = ViewState.Loading
            val session = preferences.session(sessionId = sessionId).first()
            _breathworkState.emit(ViewState.Success(success = session!!))
        }
    }

    fun navigateTo() = navigator.navigate(Screen.BreathworkDetailScreen.createRoute(sessionId))
}