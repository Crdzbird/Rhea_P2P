package rhea.group.app.ui.screens.authentication.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rhea.group.app.models.AuthResponse
import rhea.group.app.models.ErrorResponse
import rhea.group.app.models.NetworkState
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.Screen
import rhea.group.app.preferences.PrefsModeImpl
import rhea.group.app.ui.screens.authentication.repository.EmailAuthRepository
import rhea.group.app.ui.screens.authentication.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class EmailAuthViewModel @Inject constructor(
    private val emailAuthRepository: EmailAuthRepository,
    private val profileRepository: ProfileRepository,
    private val navigator: ComposeNavigator,
    val preferences: PrefsModeImpl
) : ViewModel() {

    private val _authResponse = MutableStateFlow(null as Any?)
    val authResponse = _authResponse.asStateFlow()

    private val _authenticationNetworkState: MutableState<NetworkState> =
        mutableStateOf(NetworkState.IDLE)
    val authenticationNetworkState: State<NetworkState> get() = _authenticationNetworkState

    private val authFlow: MutableSharedFlow<Int> = MutableStateFlow(1)

    suspend fun authenticate(email: String, password: String) = authFlow.flatMapLatest {
        emailAuthRepository.authenticate(
            onStart = { _authenticationNetworkState.value = NetworkState.LOADING },
            onCompletion = {},
            onError = {
                _authenticationNetworkState.value = NetworkState.ERROR
            },
            email = email,
            password = password
        )
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1).collectLatest {
        if (it is ErrorResponse) {
            _authResponse.value = it
            _authenticationNetworkState.value = NetworkState.ERROR
        }
        if (it is AuthResponse) {
            setSessionPreferences(it)
            _authResponse.value = authResponse
            _authenticationNetworkState.value = NetworkState.SUCCESS
        }
    }

    fun profileFromEmail() {
        viewModelScope.launch {
            val pref = profileRepository.fetchProfile(
                onStart = {},
                onCompletion = {},
                onError = {}
            )
            preferences.updateProfile(pref.first())
            pref.first().let {
                if (it.trialStatus == "paid") {
                    navigator.navigate(Screen.TabHolderScreen.route) {
                        popUpTo(Screen.AuthScreen.route) { inclusive = true }
                    }
                } else {
                    navigator.navigate(Screen.TrialScreen.route) {
                        popUpTo(Screen.AuthScreen.route) { inclusive = true }
                    }
                }
            }
        }
    }

    private suspend fun setSessionPreferences(authResponse: AuthResponse) {
        preferences.updateAuthResponse(s = authResponse)
        preferences.updateLogStatus(log = true)
    }
}
