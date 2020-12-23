package uz.pop.astroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.pop.astroidradar.Asteroid
import uz.pop.astroidradar.api.NetworkAsteroidContainer

@Entity
data class DatabaseAsteroid constructor(
    @PrimaryKey
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid>{
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename, closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter, relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous)
    }
}

fun NetworkAsteroidContainer.asDatabaseModel(): Array<DatabaseAsteroid>{
    return asteroids.map {
        DatabaseAsteroid(
            id = it.id,
            codename = it.codename, closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter, relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous)
    }.toTypedArray()
}

