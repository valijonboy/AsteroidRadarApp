package uz.pop.astroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException
import uz.pop.astroidradar.database.AsteroidDatabase.Companion.getDatabase
import uz.pop.astroidradar.repository.AsteroidRepository

class RefreshDataWork(appContext: Context, parameters: WorkerParameters):
CoroutineWorker(appContext, parameters){

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.refreshAsteroids()
            repository.getPicturesFromNetwork()
            Result.success()
        }catch (e: HttpException){
            Result.retry()
        }
    }

    companion object{
        const val WORK_NAME = "RefreshDataWorker"
    }
}