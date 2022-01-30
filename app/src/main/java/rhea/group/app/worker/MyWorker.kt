package rhea.group.app.worker


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters


class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    companion object {
        fun createInputData(): Data {
            return Data.Builder().build()
        }
    }

    override suspend fun doWork(): Result {
        return Result.success()
    }


}