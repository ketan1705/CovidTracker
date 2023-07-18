package android.com.example.covidtracker.ui

import android.com.example.covidtracker.R
import android.com.example.covidtracker.adapter.AdapterClass
import android.com.example.covidtracker.viewModels.MainActivityViewModel
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbb20.CountryCodePicker
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var countryCodePicker: CountryCodePicker
    private lateinit var totalCases: TextView
    private lateinit var todayCases: TextView
    private lateinit var totalActive: TextView
    private lateinit var totalRecovered: TextView
    private lateinit var todayRecovered: TextView
    private lateinit var totalDeath: TextView
    private lateinit var todayDeath: TextView
    private lateinit var totalTests: TextView
    private lateinit var spinner: Spinner
    private lateinit var mFilter: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var pieChart: PieChart
    private lateinit var adapter: AdapterClass
    private lateinit var viewModel: MainActivityViewModel
    private val types = arrayOf("cases", "death", "active", "recovered")

    private lateinit var country: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        countryCodePicker = findViewById(R.id.countryCodeHolder)
        totalCases = findViewById(R.id.total_cases)
        todayCases = findViewById(R.id.today_total_cases)
        totalDeath = findViewById(R.id.death_cases)
        todayDeath = findViewById(R.id.today_death_cases)
        totalActive = findViewById(R.id.active_cases)
        totalRecovered = findViewById(R.id.recovered_cases)
        todayRecovered = findViewById(R.id.today_recovered_cases)
        totalTests = findViewById(R.id.tests_cases)
        spinner = findViewById(R.id.spinner)
        mFilter = findViewById(R.id.filter)
        recyclerView = findViewById(R.id.recycler_view)
        pieChart = findViewById(R.id.pieChart)

        adapter = AdapterClass(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.countryData.observe(this, { countryData ->
            adapter.modelList = countryData
            adapter.notifyDataSetChanged()
        })

        spinner.onItemSelectedListener = this
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        countryCodePicker.setAutoDetectedCountry(true)
        country = countryCodePicker.selectedCountryName
        countryCodePicker.setOnCountryChangeListener {
            country = countryCodePicker.selectedCountryName
            fetchData()
        }
    }

    private fun fetchData() {
        viewModel.fetchCountryData()
        viewModel.countryData.observe(this,{countryData ->
            adapter.modelList = countryData

            for(i in countryData.indices){
                val modelList = countryData[i]
                if (modelList.country ==  country){
                    totalActive.text = modelList.active
                    totalCases.text = modelList.cases
                    totalDeath.text = modelList.deaths
                    totalRecovered.text = modelList.recovered
                    totalTests.text = modelList.tests
                    todayCases.text = modelList.todayCases
                    todayDeath.text = modelList.todayDeaths
                    todayRecovered.text = modelList.todayRecovered

                    val active = modelList.active.toInt()
                    val deaths = modelList.deaths.toInt()
                    val cases = modelList.cases.toInt()
                    val recovered = modelList.recovered.toInt()

                    updateGraph(active,cases,deaths,recovered)



                }

            }
        })

    }

    private fun updateGraph(active: Int, cases: Int, deaths: Int, recovered: Int) {
        pieChart.clearChart()
        pieChart.addPieSlice(PieModel("Cases", cases.toFloat(), Color.parseColor("#135ed0")))
        pieChart.addPieSlice(PieModel("Active", active.toFloat(), Color.parseColor("#038A09")))
        pieChart.addPieSlice(PieModel("Recovered", recovered.toFloat(), Color.parseColor("#D3BF13")))
        pieChart.addPieSlice(PieModel("Deaths", deaths.toFloat(), Color.parseColor("#BD1417")))
        pieChart.startAnimation()

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val itemText = types[position]
        mFilter.text = itemText
        adapter.filter(itemText)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

