package uz.pop.astroidradar.pictureoftheday

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "picture_of_day")
data class DatabasePicture constructor(
    @PrimaryKey
    val mediaType: String, val title: String,
    val url: String)

