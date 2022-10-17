package org.mobro4046.mobro2

import android.provider.ContactsContract
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import org.mobro4046.mobro2.model.Data
import org.mobro4046.mobro2.model.ListProvinsi
import retrofit2.http.GET

enum class ApiStatus { Loading, Success, Failed }
object Covid19Api {
    private const val BASE_URL = "https://data.covid19.go.id/public/api/"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()
    val service: ApiService by lazy { retrofit.create(ApiService::class.java) }

    interface ApiService {
        @GET("update.json")
        suspend fun getData(): Data

        @GET("prov.json")
        suspend fun getProvinsi(): ListProvinsi
    }
}