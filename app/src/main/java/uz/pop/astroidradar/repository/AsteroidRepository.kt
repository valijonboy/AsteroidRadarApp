package uz.pop.astroidradar.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.pop.astroidrad.AsteroidApi
import uz.pop.astroidradar.api.Asteroid
import uz.pop.astroidradar.api.parseStringToAsteroidList
import uz.pop.astroidradar.database.AsteroidDatabase
import uz.pop.astroidradar.database.DatabaseAsteroid
import uz.pop.astroidradar.database.asDomainModel
import uz.pop.astroidradar.pictureoftheday.DatabasePicture
import uz.pop.astroidradar.pictureoftheday.PictureApi
import uz.pop.astroidradar.pictureoftheday.PictureOfDay
import java.time.LocalDate


class AsteroidRepository(private val database: AsteroidDatabase) {

    @RequiresApi(Build.VERSION_CODES.O)
    private val startDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    private val endDate = startDate.plusDays(7)

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    val todaysAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getTodaysAsteroids(startDate.toString())){
        it.asDomainModel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val weeklyAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getWeeklyAsteroids(startDate.toString(), endDate.toString())){
            it.asDomainModel()
        }

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            val jsonResult = AsteroidApi.retrofitService.getProperties()
            val asteroids = parseStringToAsteroidList(jsonResult)
            val photosAsteroids = mutableListOf<DatabaseAsteroid>()
            print(asteroids)

            for (asteroid in asteroids){
                val databaseAsteroid = DatabaseAsteroid(
                    asteroid.id, asteroid.codename, asteroid.closeApproachDate,
                    asteroid.absoluteMagnitude, asteroid.estimatedDiameter,
                    asteroid.relativeVelocity, asteroid.distanceFromEarth,
                    asteroid.isPotentiallyHazardous
                )

                photosAsteroids.add(databaseAsteroid)
            }
            database.asteroidDao.insertAll(photosAsteroids.toList())
        }
    }

    suspend fun getPicturesFromNetwork(){
        withContext(Dispatchers.IO){
           val pictures = PictureApi.retrofitService.getPictures()
           val dbPicture = DatabasePicture(pictures.mediaType, pictures.title, pictures.url)
            database.asteroidDao.insertPicture(dbPicture)
        }
    }

    @WorkerThread
    suspend fun getPicturesAsteroids(): PictureOfDay{
        val dbPictureOfDay = database.asteroidDao.getPicture()
        return PictureOfDay(dbPictureOfDay.mediaType, dbPictureOfDay.title, dbPictureOfDay.url)
    }
}