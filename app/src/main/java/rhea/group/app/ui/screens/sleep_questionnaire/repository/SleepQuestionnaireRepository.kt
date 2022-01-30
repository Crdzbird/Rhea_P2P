package rhea.group.app.ui.screens.sleep_questionnaire.repository

import com.google.gson.Gson
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
import rhea.group.app.models.ErrorResponse
import rhea.group.app.models.SleepAnswer
import rhea.group.app.network.RheaService
import javax.inject.Inject

class SleepQuestionnaireRepository @Inject constructor(
    private val rheaService: RheaService
) : Repository {
    fun sleepQuestionnaireCall(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: suspend (statusCode: StatusCode?) -> Unit,
        session: String,
        sleepAnswer: SleepAnswer
    ) =
        flow {
            rheaService.completeWorkout(session = session, completeWorkout = sleepAnswer)
                .suspendOnSuccess {
                    emit(data)
                    onCompletion()
                }.suspendOnError {
                    errorBody?.let {
                        val errorResponse =
                            Gson().fromJson(it.charStream(), ErrorResponse::class.java)
                        emit(errorResponse)
                        onError(statusCode)
                    }
                }.suspendOnException {
                    onError(null)
                }
        }.onStart {
            onStart()
        }.onCompletion {
            onCompletion()
        }.flowOn(Dispatchers.IO)
}