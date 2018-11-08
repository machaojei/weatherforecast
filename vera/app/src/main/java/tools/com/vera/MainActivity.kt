package tools.com.vera

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow
import interfaces.heweather.com.interfacesmodule.bean.alarm.Alarm
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.Forecast
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.ForecastBase
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.Lifestyle
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.LifestyleBase
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now
import interfaces.heweather.com.interfacesmodule.view.HeConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_simple_navigation_drawer.*
import kotlinx.android.synthetic.main.air_quility_layout.*
import kotlinx.android.synthetic.main.loading_dialog_layout.*
import kotlinx.android.synthetic.main.weather_forecast_3_10_day.*
import kotlinx.android.synthetic.main.weather_life_layout.*
import tools.com.vera.about.AboutActivity
import tools.com.vera.airquility.HttpWeatherAirQuility
import tools.com.vera.airquility.IWeatherAirQuility
import tools.com.vera.airquility.WeatherAirNowQuilityView
import tools.com.vera.alarm.IWeatherAlarmResult
import tools.com.vera.citymanage.CityManageActivity
import tools.com.vera.constant.UserInfo
import tools.com.vera.http.weathernow.HttpWeatherNow
import tools.com.vera.http.weathernow.IWeatherNowResult
import tools.com.vera.lifestyle.WeatherLifeItemView
import tools.com.vera.lifestyle.weatherdatelist.HttpWeatherLifeStyle
import tools.com.vera.lifestyle.weatherdatelist.IWeatherLifeStyle
import tools.com.vera.linechart.WeatherForecastView
import tools.com.vera.linechart.weatherforecast.HttpWeatherForecast
import tools.com.vera.linechart.weatherforecast.IWeatherForecast
import tools.com.vera.wind.WeatherWindView
import java.util.concurrent.CountDownLatch


/**
 * 天气预报
 * */
class MainActivity : AppCompatActivity(), IWeatherNowResult, IWeatherLifeStyle, IWeatherForecast,
        IWeatherAirQuility, IWeatherAlarmResult,GestureDetector.OnGestureListener {

    var countDownLatch = CountDownLatch(4)

    lateinit var gestureDetector : GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showDialog()
        initLeftView()
        initEvent()
        gestureDetector = GestureDetector(this,this)
        HeConfig.init(UserInfo.USER_ID, UserInfo.USER_KEY)
        HeConfig.switchToFreeServerNode()
    }

    fun refreshData() {
        HttpWeatherNow.getWeather(this.applicationContext, this)
        HttpWeatherLifeStyle.getWeatherLifeStyle(this.applicationContext, this)
        HttpWeatherForecast.getWeatherForecast(this.applicationContext, this)
        HttpWeatherAirQuility.getAirNow(this.applicationContext, this)
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    fun initLeftView() {
        var view = LayoutInflater.from(this).inflate(R.layout.activity_simple_navigation_drawer, null)
        fast_navigation_layout.addView(view)
        navigation_view!!.itemIconTintList = null
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        val minMove = 120f         //最小滑动距离
        val minVelocity = 0f      //最小滑动速度
        val beginX = e1!!.getX()
        val endX = e2!!.getX()
        val beginY = e1!!.getY()
        val endY = e2!!.getY()

        if (beginX - endX > minMove && Math.abs(velocityX) > minVelocity) {   //左滑
            fast_navigation_layout.visibility = View.GONE

        } else if (endX - beginX > minMove && Math.abs(velocityX) > minVelocity) {   //右滑
            fast_navigation_layout.visibility = View.VISIBLE

        } else if (beginY - endY > minMove && Math.abs(velocityY) > minVelocity) {   //上滑

        } else if (endY - beginY > minMove && Math.abs(velocityY) > minVelocity) {   //下滑

        }

        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    fun initEvent() {

//        rl_main.setOnClickListener {
//            fast_navigation_layout.visibility = View.GONE
//        }

        img_left.setOnClickListener {

            fast_navigation_layout.visibility = View.VISIBLE
            fast_navigation_layout.animation = AnimationUtils.loadAnimation(this, R.anim.in_from_left)
        }

        navigation_view.setNavigationItemSelectedListener { it ->
            when (it.getItemId()) {

                R.id.item_city -> startCityManage()

                R.id.item_setting -> startAbout()

                R.id.item_about -> startAbout()

                else -> startAbout()

            }
        }
    }

    fun startCityManage(): Boolean {
        val intent1 = Intent()
        intent1.setClass(this, CityManageActivity::class.java)
        startActivity(intent1)
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right)
        return false
    }

    fun startAbout(): Boolean {
        val intent1 = Intent()
        intent1.setClass(this, AboutActivity::class.java)
        startActivity(intent1)
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right)
        return false
    }


    fun showDialog() {
        var view = LayoutInflater.from(this).inflate(R.layout.loading_dialog_layout, null)
        frame_layout.addView(view)
        loading_image.startAnimation(AnimationUtils.loadAnimation(this, R.anim.progress_anim))
    }

    fun dismiss() {
        countDownLatch.countDown()
        if (countDownLatch.count.toInt() == 0) {
            frame_layout.removeAllViews()
        }
    }

    /**
     * 获取当前天气
     * */
    override fun weatherNowLoadSuccess(result: List<Now>?) {
        dismiss()

        var noBase = result!!.get(0).now
        var basic = result.get(0).basic
        var update = result.get(0).update

        tv_cond.text = noBase!!.cond_txt
        tv_tmp.text = noBase.tmp

        var time = update!!.loc.subSequence(update.loc.length - 5, update.loc.length)
        var sb = StringBuilder()
        sb.append(time)
        sb.append("更新")
        //　最近更新时间
        tv_update_time.text = sb.toString()
        //　城市
        tv_city_name.text = basic!!.location
        // 体感温度
        tv_fl.setText("体感温度：" + noBase.fl + "℃")

        //　显示风和湿度
        var weatherWindView = WeatherWindView(this)
        weatherWindView.createItemView(this, noBase)
    }


    /**
     * 获取生活指数
     * */
    override fun weatherLifeStyleLoadSuccess(result: List<Lifestyle>?) {
        dismiss()

        for (life: LifestyleBase in result!![0].lifestyle) {
            var stringBuilder = StringBuilder()
            stringBuilder.append(HttpWeatherLifeStyle.getLifeType(life.type))
            stringBuilder.append(" ")
            stringBuilder.append(life.brf)

            var view = WeatherLifeItemView(this)
            view.createItemView(stringBuilder.toString(), life)
            tv_life_layout.addView(view)
        }

    }

    /**
     * 获取未来三天天气
     * */
    override fun weatherForecastLoadSuccess(result: List<Forecast>?) {
        dismiss()
        var index = 0

        if (result!![0].daily_forecast == null) {
            return
        }

        if (result!![0].daily_forecast[0] != null) {
            var mForecastBase = result!![0].daily_forecast[0]
            tv_sun_sr.setText("日出时间：" + mForecastBase.sr)
            tv_sun_ss.setText("日落时间：" + mForecastBase.ss)
        }

        var week = 1
        for (forecast: ForecastBase in result!![0].daily_forecast) {
            if (week > 3) {
                return
            }

            var view = WeatherForecastView(this)
            view.createItemView(week, forecast)
            week++
            tmp_linear.addView(view)
        }
    }

    /**
     * 空气污染物结果
     * */
    override fun weatherAirQuilityLoadSuccess(result: List<AirNow>?) {
        dismiss()

        var airNowCity = result!![0].air_now_city

        var list = arrayListOf("PM10[可吸入颗粒物]", "PM2.5[细颗粒物]", "NO2[二氧化氮]", "SO2[二氧化硫]", "CO[一氧化碳]", "O3[臭氧]")
        var quilityList = arrayListOf(airNowCity.pm10, airNowCity.pm25, airNowCity.no2, airNowCity.so2, airNowCity.co, airNowCity.o3)
        for (index in list.indices) {
            var view = WeatherAirNowQuilityView(this)
            view.createItemView(this, list[index], quilityList[index])
            air_linear!!.addView(view)
        }

        tv_air_q.setText(airNowCity.qlty + " " + airNowCity.aqi)
    }

    /**
     * 天气预警结果
     * */
    override fun weatherAlarmLoadSuccess(result: List<Alarm>?) {

        if (result!![0].alarm.isEmpty()) {

        }


    }
}
