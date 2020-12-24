package uz.pop.astroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import uz.pop.astroidrad.AsteroidApi
import uz.pop.astroidradar.Asteroid
import uz.pop.astroidradar.api.parseAsteroidsJsonResult
import uz.pop.astroidradar.database.AsteroidDatabase
import uz.pop.astroidradar.database.DatabaseAsteroid
import uz.pop.astroidradar.database.asDomainModel

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            val jsonResult = AsteroidApi.retrofitService.getProperties()
            val asteroids = parseAsteroidsJsonResult(JSONObject(jsonResult))
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
}