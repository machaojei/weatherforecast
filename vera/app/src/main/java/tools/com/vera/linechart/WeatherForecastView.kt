package tools.com.vera.linechart

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.ForecastBase
import kotlinx.android.synthetic.main.weather_forecast_future_item_layout.view.*
import tools.com.vera.R
import java.util.*


class WeatherForecastView(context: Context?) : RelativeLayout(context) {


    init {
        LayoutInflater
                .from(context)
                .inflate(R.layout.weather_forecast_future_item_layout, this, true)
    }


     fun createItemView(week: Int, forecastBase: ForecastBase) {
        tv_tmp_max.setText(forecastBase.tmp_max + "℃")
        tv_tmp_min.setText(forecastBase.tmp_min + "℃")

        tv_week.setText(getWeek(week))
        tv_day_weather_cond.setText(forecastBase.cond_txt_d)
        val drawableId = getResources().getIdentifier("a" + forecastBase.cond_code_d, "drawable", context.getPackageName())

        image_weather_cond.setImageDrawable(resources.getDrawable(drawableId, null))
    }


    //获取今天是星期几
    fun getWeek(num : Int): String {

        var dt = Date()
        val weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        val cal = Calendar.getInstance()
        cal.time = dt
        var w = cal.get(Calendar.DAY_OF_WEEK) - 1
        if (w < 0) {
            w = 0
        }
        if (num == 1) {

            return weekDays[w]
        }else {
            return getWeekDate(w,num)
        }
    }

    /**
     * 返回指定日期
     * */
    fun getWeekDate(current:Int,num : Int) : String {

        val weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        var week = current+num-1
        if (week > 6) {
            week = week - 7
        }
        return weekDays[week]

    }

}