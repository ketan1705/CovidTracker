package android.com.example.covidtracker.model

data class Model (

    val cases :String,
    val todayCases:String,
    val deaths:String,
    val todayDeaths:String,
    val recovered:String,
    val todayRecovered:String,
    val active:String,
    val tests:String,
    val country:String
)
