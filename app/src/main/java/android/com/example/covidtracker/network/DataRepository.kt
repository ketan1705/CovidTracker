package android.com.example.covidtracker.network

import android.com.example.covidtracker.model.Model
import retrofit2.Response

class DataRepository {

    private val apiService : ApiService = ApiClient.createService(ApiService::class.java)

    suspend fun getCountryData():Response<List<Model>>
    {
        return apiService.getCountryData()
    }
}
