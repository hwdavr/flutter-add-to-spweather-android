package com.demo.weather.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.demo.weather.R
import com.demo.weather.databinding.ActivityWeatherBinding
import com.demo.weather.model.util.CITY_ID
import com.demo.weather.viewmodel.CityWeatherViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class CityWeatherActivity : AppCompatActivity() {
    private val TAG = CityWeatherActivity::class.java.simpleName
    private lateinit var viewModel: CityWeatherViewModel
    private lateinit var binding: ActivityWeatherBinding
    private var city: String? = null
    private lateinit var context: Context

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        with(binding) {
            lifecycleOwner = this@CityWeatherActivity
            weatherIconUrl = ""
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        context = this
        city = intent.getStringExtra(CITY_ID)

        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CityWeatherViewModel::class.java)
        binding.viewModel = viewModel


        viewModel.weatherIconUrlLiveData.observe(this, Observer<String> {
            Log.d(TAG, "Weather Icon updated")
            binding.weatherIconUrl = it
        })
    }

    override fun onResume() {
        super.onResume()
        if (city != null) {
            viewModel.checkCurrentWeather(city!!)
        } else {
            Log.e(TAG, "Cannot get city name")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}