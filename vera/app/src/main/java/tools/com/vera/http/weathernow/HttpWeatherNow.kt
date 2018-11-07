package tools.com.vera.http.weathernow

import android.content.Context
import android.util.Log
import interfaces.heweather.com.interfacesmodule.bean.Lang
import interfaces.heweather.com.interfacesmodule.bean.Unit
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now
import interfaces.heweather.com.interfacesmodule.view.HeWeather
import tools.com.vera.constant.UserInfo

object HttpWeatherNow {

     /**
      * 实况天气
      * 实况天气即为当前时间点的天气状况以及温湿风压等气象指数，具体包含的数据：体感温度、
      * 实测温度、天气状况、风力、风速、风向、相对湿度、大气压强、降水量、能见度等。
      *
      * @param  context  上下文
      * @param location 地址详解
      * @param lang       多语言，默认为简体中文
      * @param unit        单位选择，公制（m）或英制（i），默认为公制单位
      * @param listener  网络访问回调接口
      */
    fun getWeather(mContext : Context,mIWeatherNowResult: IWeatherNowResult) {
        HeWeather.getWeatherNow(mContext, UserInfo.getCityId(mContext), Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                object : HeWeather.OnResultWeatherNowBeanListener {
                    override fun onSuccess(p0: List<Now>?) {
                        mIWeatherNowResult.weatherNowLoadSuccess(p0);
                    }

                    override fun onError(e: Throwable) {
                        Log.i("Log", "onError: ", e)
                    }
                })

    }

}