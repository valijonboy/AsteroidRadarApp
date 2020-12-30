package uz.pop.astroidradar.api

import com.squareup.moshi.JsonClass
import uz.pop.astroidradar.database.DatabaseAsteroid
import uz.pop.astroidradar.pictureoftheday.DatabasePicture
import uz.pop.astroidradar.pictureoftheday.PictureOfDay

@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(
//    @Json(name = "asteroids")
    val asteroids: List<Asteroid>)

@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean)


/**
 * Convert Network results to database objects
 */
fun NetworkAsteroidContainer.asDomainModel(): List<Asteroid>{
    return asteroids.map{
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
            id = it.id, codename = it.codename, closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter, relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous)
    }.toTypedArray()
}

@JsonClass(generateAdapter = true)
data class NetworkPictureOfDayContainer(val picture: PictureOfDay)

@JsonClass(generateAdapter = true)
data class NetworkPictureOfDay(
    val mediaType: String,
    val title: String,
    val url: String)

fun NetworkPictureOfDayContainer.asDomainModel(): PictureOfDay{
    return PictureOfDay(picture.mediaType, picture.title, picture.url)
}

fun NetworkPictureOfDayContainer.asDatabaseModel(): DatabasePicture{
    return DatabasePicture(picture.mediaType, picture.title, picture.url)
}