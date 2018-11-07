package tools.com.vera.lifestyle.weatherdatelist

import android.content.Context
import android.util.Log
import interfaces.heweather.com.interfacesmodule.bean.Lang
import interfaces.heweather.com.interfacesmodule.bean.Unit
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.Lifestyle
import interfaces.heweather.com.interfacesmodule.view.HeWeather
import tools.com.vera.constant.UserInfo

object HttpWeatherLifeStyle {


    /**
     * 生活指数
     *
     * @param  context  上下文
     * @param location 地址详解
     * @param lang       多语言，默认为简体中文
     * @param unit        单位选择，公制（m）或英制（i），默认为公制单位
     * @param listener  网络访问回调接口
     */
    fun getWeatherLifeStyle(mContext : Context, mIWeatherLifeStyle: IWeatherLifeStyle) {
        HeWeather.getWeatherLifeStyle(mContext, UserInfo.getCityId(mContext), Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                object : HeWeather.OnResultWeatherLifeStyleBeanListener {
                    override fun onSuccess(p0: MutableList<Lifestyle>?) {
                        mIWeatherLifeStyle.weatherLifeStyleLoadSuccess(p0)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("Log", "onError: ", e)
                    }
                })

    }

    /**
     * 获取生活指数类型
     * */
    fun getLifeType(type : String): String? {
        when(type) {
            "comf" -> return "舒适度指数"
            "cw" -> return "洗车指数"
            "drsg" -> return "穿衣指数"
            "flu" -> return "感冒指数"
            "sport" -> return "运动指数"
            "trav" -> return "旅游指数"
            "uv" -> return "紫外线指数"
            "air" -> return "空气污染扩散条件指数"
            "ac" -> return "空调开启指数"
            "ag" -> return "过敏指数"
            "gl" -> return "太阳镜指数"
            "mu" -> return "化妆指数"
            "airc" -> return "晾晒指数"
            "ptfc" -> return "交通指数"
            "spi" -> return "防晒指数"
            else -> return null
        }
    }

}