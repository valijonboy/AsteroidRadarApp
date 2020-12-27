package uz.pop.astroidradar.main

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import uz.pop.astroidradar.Asteroid
import uz.pop.astroidradar.repository.AsteroidRepository

enum class AsteroidStatus{ LOADING, ERROR, DONE}

enum class AsteroidFilter{ ALL, WEEKLY, TODAY }

class MainViewModel( private val repository : AsteroidRepository): ViewModel() {

    private val _status = MutableLiveData<AsteroidStatus>()
    val status: LiveData<AsteroidStatus> get() = _status

    private val filters = MutableLiveData(AsteroidFilter.WEEKLY)

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids = Transformations.switchMap(filters){
       repository.asteroids
    }


    init {
        viewModelScope.launch {
            getAsteroidsStatus()
        }
    }

    private fun getAsteroidsStatus(){
        viewModelScope.launch {

            try {
                _status.value = AsteroidStatus.LOADING
               repository.refreshAsteroids()
            }catch (e: Exception){
                _status.value = AsteroidStatus.ERROR
                _asteroids.value = ArrayList()
            }finally {
                _status.value = AsteroidStatus.DONE
            }
        }
    }

    fun setFilter(filter: AsteroidFilter){
        filters.postValue(filter)
    }


    class Factory(private val repository: AsteroidRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}