package uz.pop.astroidradar.pictureoftheday

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "picture_of_day")
data class DatabasePicture constructor(
    @PrimaryKey
    val mediaType: String, val title: String,
    val url: String)

