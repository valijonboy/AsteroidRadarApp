package uz.pop.astroidradar.pictureoftheday

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import uz.pop.astroidradar.utils.Constants.BASE_URL
import uz.pop.astroidradar.utils.Constants.CLIENT_ID

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PictureApiService {
    @GET("planetary/apod?api_key=${CLIENT_ID}")
    suspend fun getPictures(): PictureOfDay
}

object PictureApi {
    val retrofitService: PictureApiService by
    lazy {
        retrofit.create(PictureApiService::class.java)
    }
}