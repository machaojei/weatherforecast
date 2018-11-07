package tools.com.vera.constant

import android.content.Context
import tools.com.vera.util.Preference

object UserInfo {

    var USER_KEY = "b86cd1c3a01347b7b7f2f3cf5e0037c7"
    var USER_ID = "HE1804241116061254"

    var sharedPreferences = "city_config"
    var default_city = "default_city"
    var default_city_key = "cityid"

    fun getCityId(context: Context) : String {
        var userId by Preference(context, default_city_key, "CN101210101")
        var cityid = userId
        return cityid
    }

}