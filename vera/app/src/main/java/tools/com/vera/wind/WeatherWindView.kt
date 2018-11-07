package tools.com.vera.wind

import android.app.Activity
import android.content.Context
import android.widget.RelativeLayout
import android.widget.TextView
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase
import tools.com.vera.R

class WeatherWindView(context: Context?) : RelativeLayout(context) {

    /**
     * 设置风向相关内容
     * */
     fun createItemView(activity: Activity, nowBase: NowBase){


        var tv_wind_dir_num = activity.findViewById<TextView>(R.id.tv_wind_dir_num)
        var tv_wind_sc_num = activity.findViewById<TextView>(R.id.tv_wind_sc_num)
        var tv_hum_num = activity.findViewById<TextView>(R.id.tv_hum_num)


        tv_wind_dir_num!!.setText(nowBase!!.wind_dir)
        tv_wind_sc_num!!.setText(nowBase!!.wind_sc+"级")
        tv_hum_num!!.setText(nowBase!!.hum+"%")
    }


}