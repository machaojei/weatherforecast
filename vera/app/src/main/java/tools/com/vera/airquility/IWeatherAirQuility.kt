package tools.com.vera.airquility

import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow

interface IWeatherAirQuility {

    fun weatherAirQuilityLoadSuccess(result : List<AirNow>?)

}