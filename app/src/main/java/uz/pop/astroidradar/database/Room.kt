package uz.pop.astroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {
    @Query("select * from databaseasteroid order by closeApproachDate desc")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroids: List<DatabaseAsteroid>)

    @Query("select * from databaseasteroid where closeApproachDate = :date order by closeApproachDate desc")
    fun getTodaysAsteroids(date: String): LiveData<List<DatabaseAsteroid>>

    @Query("select * from databaseasteroid where closeApproachDate between :startDate and :endDate")
    fun getWeeklyAsteroids(startDate: String, endDate: String): LiveData<List<DatabaseAsteroid>>


}

@Database(entities = [DatabaseAsteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getDatabase(context: Context): AsteroidDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDatabase::class.java,
                    "asteroids"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}