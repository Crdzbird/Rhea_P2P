package rhea.group.app.ui.screens.stage.repository

import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import rhea.group.app.app.Repository
import rhea.group.app.network.RheaService
import javax.inject.Inject

class SessionRepository @Inject constructor(
    private val rheaService: RheaService
) : Repository {
    fun fetchSession(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: suspend (statusCode: StatusCode?) -> Unit,
        session: String
    ) =
        flow {
            rheaService.session(session = session).suspendOnSuccess {
                emit(data)
                onCompletion()
            }.suspendOnError {
                onError(statusCode)
            }.suspendOnException {
                onError(null)
            }
        }.onStart {
            onStart()
        }.onCompletion {
            onCompletion()
        }.flowOn(Dispatchers.IO)
}