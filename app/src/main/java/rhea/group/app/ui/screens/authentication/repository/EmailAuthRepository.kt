package rhea.group.app.ui.screens.authentication.repository

import com.google.gson.Gson
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import rhea.group.app.app.Repository
import rhea.group.app.models.AuthCredentials
import rhea.group.app.models.ErrorResponse
import rhea.group.app.network.RheaService
import javax.inject.Inject

class EmailAuthRepository @Inject constructor(
    private val rheaService: RheaService
) : Repository {

    fun authenticate(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (statusCode: StatusCode?) -> Unit,
        email: String,
        password: String
    ) =
        flow {
            val authCredentials = AuthCredentials(email = email, password = password)
            rheaService.authenticate(authCredentials = authCredentials).suspendOnSuccess {
                emit(data)
                onCompletion()
            }.suspendOnError {
                errorBody?.let {
                    val errorResponse = Gson().fromJson(it.charStream(), ErrorResponse::class.java)
                    emit(errorResponse)
                    onError(statusCode)
                }
            }.onException {
                onError(null)
            }
        }.onStart {
            onStart()
        }.flowOn(Dispatchers.IO)
}