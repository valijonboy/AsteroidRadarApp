package uz.pop.astroidradar.pictureoftheday

import com.squareup.moshi.Json

data class PictureOfDay( val mediaType: String, val title: String,
                        val url: String)