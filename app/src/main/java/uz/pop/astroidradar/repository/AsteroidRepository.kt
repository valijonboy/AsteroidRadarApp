package uz.pop.astroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.pop.astroidrad.AsteroidApi
import uz.pop.astroidradar.Asteroid
import uz.pop.astroidradar.api.asDatabaseModel
import uz.pop.astroidradar.database.AsteroidDatabase
import uz.pop.astroidradar.database.asDomainModel

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            val photosAsteroids = AsteroidApi.retrofitService.getProperties().await()
            database.asteroidDao.insertAll(*photosAsteroids.asDatabaseModel())
        }
    }
}