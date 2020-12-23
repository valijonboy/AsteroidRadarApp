package uz.pop.astroidrad

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import uz.pop.astroidradar.Constants.BASE_URL
import uz.pop.astroidradar.Constants.CLIENT_ID
import uz.pop.astroidradar.api.NetworkAsteroidContainer

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface AsteroidApiService {

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("neo/rest/v1/feed?&api_key=${CLIENT_ID}")
    suspend fun getProperties(): Deferred<NetworkAsteroidContainer>
}

object AsteroidApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val retrofitService = retrofit.create(AsteroidApiService::class.java)
}
