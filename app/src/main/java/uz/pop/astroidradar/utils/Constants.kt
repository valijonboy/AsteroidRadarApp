package uz.pop.astroidradar.utils

import uz.pop.astroidradar.BuildConfig


object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val CLIENT_ID = BuildConfig.NASA_ACCESS_KEY
}