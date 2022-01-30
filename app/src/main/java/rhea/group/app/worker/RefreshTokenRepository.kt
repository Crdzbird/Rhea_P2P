package rhea.group.app.worker

import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rhea.group.app.models.AuthResponse
import rhea.group.app.network.RheaService
import rhea.group.app.preferences.PrefsModeImpl
import javax.inject.Inject

class RefreshTokenRepository @Inject constructor(
    private val rheaService: RheaService,
    private val preferences: PrefsModeImpl
) {
    suspend fun refreshToken() {
        val session = preferences.authResponse.first() ?: return
        rheaService.refresh(authResponse = session).suspendOnSuccess {
            preferences.updateAuthResponse(data)
        }
    }

    suspend fun fetchToken() =
        flow {
            preferences.authResponse.first()?.let {
                rheaService.refresh(authResponse = AuthResponse(refresh_token = it.refresh_token))
                    .suspendOnSuccess {
                        preferences.updateAuthResponse(data)
                        emit(StatusCode.Accepted)
                    }.suspendOnError {
                        preferences.clearSession()
                        emit(statusCode)
                    }.suspendOnException {
                        emit(StatusCode.Unknown)
                    }
            } ?: run {
                emit(StatusCode.Forbidden)
            }
        }.flowOn(Dispatchers.IO)
}