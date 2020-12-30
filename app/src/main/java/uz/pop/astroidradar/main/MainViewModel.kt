package uz.pop.astroidradar.main

import android.view.MenuItem
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import uz.pop.astroidradar.api.Asteroid
import uz.pop.astroidradar.R
import uz.pop.astroidradar.pictureoftheday.PictureApi
import uz.pop.astroidradar.pictureoftheday.PictureOfDay
import uz.pop.astroidradar.repository.AsteroidRepository

enum class AsteroidStatus { LOADING, ERROR, DONE }

enum class AsteroidFilter { ALL, WEEKLY, TODAY }

class MainViewModel(private val repository: AsteroidRepository) : ViewModel() {

    private val _status = MutableLiveData<AsteroidStatus>()
    val status: LiveData<AsteroidStatus> get() = _status

    private val _image = MutableLiveData<PictureOfDay>()
    val image: LiveData<PictureOfDay> get() = _image

    private val pictureOfDayApi = PictureApi.retrofitService

    private val filters = MutableLiveData(AsteroidFilter.WEEKLY)

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids = Transformations.switchMap(filters) {
        when (it) {
            AsteroidFilter.WEEKLY -> repository.weeklyAsteroids
            AsteroidFilter.TODAY -> repository.todaysAsteroids
            else -> repository.asteroids
        }
    }

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()

    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid


    init {
        viewModelScope.launch {
            getPictureOfDay()
            getAsteroidsStatus()
        }
    }

    private fun getAsteroidsStatus() {
        viewModelScope.launch {

            try {
                _status.value = AsteroidStatus.LOADING
                repository.refreshAsteroids()
            } catch (e: Exception) {
                _status.value = AsteroidStatus.ERROR
                _asteroids.value = ArrayList()
            } finally {
                _status.value = AsteroidStatus.DONE
            }
        }
    }

    private fun getPictureOfDay() {

        viewModelScope.launch {
            val result = pictureOfDayApi.getPictures()
            try {
                _status.value = AsteroidStatus.LOADING
                repository.getPicturesFromNetwork()
               if (result.mediaType == "image"){
                   _image.value = repository.getPicturesAsteroids()
               }
            } catch (e: Exception) {
                _status.value = AsteroidStatus.ERROR
            } finally {
                _status.value = AsteroidStatus.DONE
            }
        }
    }

    private fun setFilter(filter: AsteroidFilter) {
        filters.postValue(filter)
    }

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

    fun menuItemSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.show_all_menu -> setFilter(AsteroidFilter.WEEKLY)
            R.id.show_rent_menu -> setFilter(AsteroidFilter.TODAY)
            else -> setFilter(AsteroidFilter.ALL)
        }
    }


    class Factory(private val repository: AsteroidRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}