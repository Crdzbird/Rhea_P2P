package rhea.group.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.StatusCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rhea.group.app.preferences.PrefsModeImpl
import rhea.group.app.ui.screens.authentication.repository.ProfileRepository
import rhea.group.app.worker.RefreshTokenRepository
import javax.inject.Inject


enum class UserStatusEnum {
    Unauthenticated,
    Offline,
    Paid,
    Free
}

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val profileRepository: ProfileRepository,
    val preferences: PrefsModeImpl
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    private val _userStatus = MutableStateFlow(UserStatusEnum.Unauthenticated)
    val userStatus = _userStatus.asStateFlow()

    init {
        viewModelScope.launch {
            refreshTokenRepository.fetchToken().distinctUntilChanged().collect {
                if (it == StatusCode.Unknown && preferences.authResponse.first() != null) {
                    _userStatus.value = UserStatusEnum.Offline
                    _isLoading.value = false
                } else if (it != StatusCode.Accepted) {
                    _userStatus.value = UserStatusEnum.Unauthenticated
                    _isLoading.value = false
                } else {
                    checkPrivilege()
                }
            }
        }
    }

    private suspend fun checkPrivilege() {
        profileRepository.fetchProfile(
            onStart = {},
            onCompletion = {},
            onError = {}
        ).distinctUntilChanged().collect { result ->
            preferences.updateProfile(result)
            _userStatus.value = if (result.trialStatus == "paid") {
                UserStatusEnum.Paid
            } else {
                UserStatusEnum.Free
            }
            _isLoading.value = false
        }
    }
}