package tools.com.vera.alarm

import interfaces.heweather.com.interfacesmodule.bean.alarm.Alarm

/**
 * 天气预警结果回调
 * */
interface IWeatherAlarmResult {

    fun weatherAlarmLoadSuccess(result : List<Alarm>?)
}