package tools.com.vera.lifestyle

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.LifestyleBase
import kotlinx.android.synthetic.main.weather_life_item.view.*
import tools.com.vera.R

class WeatherLifeItemView(context: Context?) : RelativeLayout(context) {


    init {
        LayoutInflater
                .from(context)
                .inflate(R.layout.weather_life_item, this, true)
    }


    public fun createItemView(title : String,lifestyle : LifestyleBase){

        image_life_style.setImageDrawable(resources.getDrawable(getLifeTypeImage(lifestyle.type)!!,null))
        tv_life_style_brf!!.setText(title)
        tv_life_style_text!!.setText(lifestyle.txt)
    }


    /**
     * 获取生活指数类型图片
     * */
    fun getLifeTypeImage(type : String): Int? {
        when(type) {
            "comf" -> return R.drawable.sun
            "cw" -> return R.drawable.car
            "drsg" -> return R.drawable.clouth
            "flu" -> return R.drawable.flu
            "sport" -> return R.drawable.seport
            "trav" -> return R.drawable.travel
            "uv" -> return R.drawable.light
            "air" -> return R.drawable.cloud
            "airc" -> return R.drawable.light
            "ptfc" -> return R.drawable.car
            "spi" -> return R.drawable.sun
            else -> return null
        }
    }

}