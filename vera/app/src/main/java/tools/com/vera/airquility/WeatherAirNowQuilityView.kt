package tools.com.vera.airquility

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.air_quility_item.view.*
import tools.com.vera.R

class WeatherAirNowQuilityView(context: Context?) : RelativeLayout(context) {

    init {
        LayoutInflater
                .from(context)
                .inflate(R.layout.air_quility_item, this, true)
    }

    /**
     * 设置空气质量相关内容
     * */
    public fun createItemView(activity: Activity, name : String,value : String){
        if (tv_air_name != null) {
            tv_air_name.setText(name)
        }
        if (tv_air_value != null) {
            tv_air_value.setText(value+" μg/ｍ³")
        }
    }


}
