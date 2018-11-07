package tools.com.vera.http.weathernow

import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now

/**
 * 实时天气结果回调
 * */
interface IWeatherNowResult {

    fun weatherNowLoadSuccess(result : List<Now>?)
}