package android.com.example.covidtracker.viewModels

import android.com.example.covidtracker.model.Model
import android.com.example.covidtracker.network.DataRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val repository :DataRepository = DataRepository()

    private val _countryData: MutableLiveData<List<Model>> = MutableLiveData()

    val countryData :LiveData<List<Model>> get() = _countryData

    init {
        fetchCountryData()
    }

    fun fetchCountryData(){
        viewModelScope.launch {
            try {
                val response = repository.getCountryData()
                if (response.isSuccessful)
                {
                    val data = response.body()
                    _countryData.value = data?: emptyList()
                }
            }catch (e:Exception){
                //
            }
        }
    }

}
