package uz.pop.astroidradar.main

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import uz.pop.astroidrad.AsteroidApi
import uz.pop.astroidradar.Asteroid
import uz.pop.astroidradar.database.getDatabase
import uz.pop.astroidradar.repository.AsteroidRepository
import java.lang.Exception
import java.lang.IllegalArgumentException

class MainViewModel(application: Application): AndroidViewModel(application) {

   private val database = getDatabase(application)
    private val repository = AsteroidRepository(database)

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
        }
    }

    val photosAsteroid = repository.asteroids

    class Factory(val app: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}