package rhea.group.app.worker

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import rhea.group.app.utils.UuidParser
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class WorkerViewModel @Inject constructor(
    private val workManager: WorkManager,
    private val refreshTokenRepository: RefreshTokenRepository
) : ViewModel() {
    companion object {
        const val WORK_ID = "81c578f9-5e6d-4d78-8729-f32acefc144b"
    }

    private var workInfo: LiveData<WorkInfo>
    private val uuid: UUID = UuidParser.fromString(WORK_ID)
    private val ob = Observer<WorkInfo> { t ->
        t?.run {
            when (this.state) {
                WorkInfo.State.SUCCEEDED -> runBlocking { refreshTokenRepository.refreshToken() }
                else -> {}
            }
        }
    }

    init {
        workInfo = workManager.getWorkInfoByIdLiveData(uuid)
        workInfo.observeForever(ob)
    }

    fun startWork() {
        val s = PeriodicWorkRequestBuilder<MyWorker>(
            20,
            TimeUnit.MINUTES
        ).setInputData(MyWorker.createInputData()).build()

        workManager.enqueueUniquePeriodicWork(
            WORK_ID,
            ExistingPeriodicWorkPolicy.KEEP,
            s
        )
    }

    override fun onCleared() {
        super.onCleared()
        workInfo.removeObserver(ob)
    }
}