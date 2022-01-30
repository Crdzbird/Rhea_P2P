package rhea.group.app.ui.screens.authentication.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import rhea.group.app.models.NetworkState
import rhea.group.app.models.Profile
import rhea.group.app.preferences.PrefsModeImpl
import rhea.group.app.ui.screens.authentication.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val preferences: PrefsModeImpl
) : ViewModel() {
    private var _profile = MutableStateFlow(null as Profile?)
    val profile: StateFlow<Profile?> = _profile

    private val _profileNetworkState: MutableState<NetworkState> = mutableStateOf(NetworkState.IDLE)
    val profileNetworkState: State<NetworkState> get() = _profileNetworkState

    private val profileFlow: MutableSharedFlow<Int> = MutableSharedFlow(1)

    suspend fun fetchProfile() = profileFlow.flatMapLatest {
        profileRepository.fetchProfile(
            onStart = { _profileNetworkState.value = NetworkState.LOADING },
            onCompletion = { _profileNetworkState.value = NetworkState.SUCCESS },
            onError = {})
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1).collectLatest {
        preferences.updateProfile(it)
        _profile.value = it
    }
}