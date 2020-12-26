package com.demo.weather.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.weather.R
import com.demo.weather.model.apidata.City

class HomeScreenAdapter: RecyclerView.Adapter<CityViewHolder>() {
    private var cities = arrayListOf<City>()

    var delegate: CityViewHolder.Delegate? = null

    fun updateData(data: List<City>) {
        cities.clear()
        cities.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]

        holder.cityName.text = city.name
        holder.view.setOnClickListener {
            delegate?.onItemClick(city.name, holder.view)
        }
    }
}
