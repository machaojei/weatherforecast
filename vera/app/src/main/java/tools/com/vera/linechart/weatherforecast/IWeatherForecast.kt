package tools.com.vera.linechart.weatherforecast

import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.Forecast

interface IWeatherForecast {

    fun weatherForecastLoadSuccess(result : List<Forecast>? )
}