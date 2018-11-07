package tools.com.vera.citymanage.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import tools.com.vera.R
import tools.com.vera.citymanage.data.CityVo
import tools.com.vera.constant.UserInfo
import tools.com.vera.util.Preference

/**
 * @date: 18-9-7
 *
 * @author bazhuayu
 *
 * @description
 */
class CityAdapter(val context: Context, val citylist: ArrayList<CityVo>, var result: Result) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityAdapter.CityViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_city, parent, false))
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.cityname.setText(citylist[position].cityName)
        holder.provincename.setText(citylist[position].province)
        holder.view.setOnClickListener {

            var userId by Preference(context, UserInfo.default_city_key, "CN101210101")
            userId = "CN" + citylist[position].cityId
            result.click()
        }
    }


    override fun getItemCount(): Int {
        return citylist.size
    }


    class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cityname: TextView = view.findViewById(R.id.tv_cityname)
        var provincename: TextView = view.findViewById(R.id.tv_provincename)

        val view: RelativeLayout = view.findViewById(R.id.item_city_layout)
    }


    interface Result {
        fun click()
    }

}