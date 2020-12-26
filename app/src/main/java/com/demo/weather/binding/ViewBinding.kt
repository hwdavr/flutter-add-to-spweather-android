package com.demo.weather.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.demo.weather.viewmodel.CityWeatherViewModel

@BindingAdapter("weatherIcon")
fun loadImage(view: ImageView, weatherIconUrl: String) {
    Glide.with(view.getContext())
        .load(weatherIconUrl)
        .into(view)
}
