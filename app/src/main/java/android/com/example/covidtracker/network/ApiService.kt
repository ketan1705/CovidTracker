package android.com.example.covidtracker.network

import android.com.example.covidtracker.model.Model
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("countries")
    suspend fun getCountryData(): Response<List<Model>>
}