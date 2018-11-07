package tools.com.vera.linechart.weatherforecast

import android.content.Context
import android.util.Log
import interfaces.heweather.com.interfacesmodule.bean.Lang
import interfaces.heweather.com.interfacesmodule.bean.Unit
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.Forecast
import interfaces.heweather.com.interfacesmodule.view.HeWeather
import tools.com.vera.constant.UserInfo


object HttpWeatherForecast {

    /**
     * 3-10天天气预报
     *
     * @param  context  上下文
     * @param location 地址详解
     * @param lang       多语言，默认为简体中文
     * @param unit        单位选择，公制（m）或英制（i），默认为公制单位
     * @param listener  网络访问回调接口
     */
    fun getWeatherForecast(mContext : Context, mIWeatherForecast: IWeatherForecast) {
        HeWeather.getWeatherForecast(mContext, UserInfo.getCityId(mContext), Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                object : HeWeather.OnResultWeatherForecastBeanListener{
                    override fun onSuccess(p0: MutableList<Forecast>?) {
                        mIWeatherForecast.weatherForecastLoadSuccess(p0)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("Log", "onError: ", e)
                    }
                })

    }

}