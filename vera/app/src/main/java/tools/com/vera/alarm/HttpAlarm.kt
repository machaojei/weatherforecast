package tools.com.vera.alarm

import android.content.Context
import android.util.Log
import interfaces.heweather.com.interfacesmodule.bean.Lang
import interfaces.heweather.com.interfacesmodule.bean.Unit
import interfaces.heweather.com.interfacesmodule.bean.alarm.Alarm
import interfaces.heweather.com.interfacesmodule.view.HeWeather
import tools.com.vera.constant.UserInfo

object HttpAlarm {


    /**
     * @param context  上下文
     * @param location (如果不添加此参数,SDK会根据GPS联网定位,根据当前经纬度查询)所查询的地区，可通过该地区名称、ID、Adcode、IP和经纬度进行查询经纬度格式：纬度,经度
     *                 （英文,分隔，十进制格式，北纬东经为正，南纬西经为负)
     * @param lang     多语言，默认为简体中文，其他语言请参照多语言对照表
     * @param unit     单位选择，公制（m）或英制（i），默认为公制单位
     * @param listener 网络访问回调接口
     */

    fun getWeatherAlarm(mContext : Context, mIWeatherAlarmResult: IWeatherAlarmResult) {
        HeWeather.getAlarm(mContext, UserInfo.getCityId(mContext), Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                object : HeWeather.OnResultAlarmBeansListener {
                    override fun onSuccess(p0: MutableList<Alarm>?) {
                        mIWeatherAlarmResult.weatherAlarmLoadSuccess(p0)
                    }


                    override fun onError(e: Throwable) {
                        Log.i("Log", "onError: ", e)
                    }
                })

    }

}