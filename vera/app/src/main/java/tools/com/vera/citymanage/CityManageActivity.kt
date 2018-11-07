package tools.com.vera.citymanage

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_city_manage.*
import tools.com.vera.R
import tools.com.vera.citymanage.adapter.CityAdapter
import tools.com.vera.citymanage.data.CityDataVo
import tools.com.vera.citymanage.data.CityVo
import tools.com.vera.util.FileUtils

/**
 * @date: 18-9-6
 *
 * @author bazhuayu
 *
 * @description 城市管理画面
 */
class CityManageActivity : AppCompatActivity(), CityAdapter.Result {

    var editString = ""
    var allCityList = CityDataVo(null)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_manage)
        try {
            var content = FileUtils.readAssestFileText(this, "data/citylist.json")
            allCityList = Gson().fromJson(content, CityDataVo::class.java)

            // 监听搜索框输入
            edit_city_name.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    if(delayRun!=null){
                        //每次editText有变化的时候，则移除上次发出的延迟线程
                        handler.removeCallbacks(delayRun)
                    }
                    editString = s.toString()
                    //延迟800ms，如果不再输入字符，则执行该线程的run方法
                    handler.postDelayed(delayRun, 800)
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })


        } catch (e: Exception) {
            Log.e("gson=====", e.printStackTrace().toString())
        }
    }

    /**
     * 搜索城市
     * */
    fun searchCityData() {
        var cityList = ArrayList<CityVo>()
        if (!allCityList.citylist!!.isEmpty()) {
            for (city : CityVo in allCityList.citylist!!) {
                if (!editString.isBlank() && city.cityName.contains(editString)) {
                    cityList!!.add(city)
                }
            }
        }

        var adapter = CityAdapter(this.applicationContext, cityList,this)
        var layoutManager = LinearLayoutManager(this)
        listview_city.layoutManager = layoutManager
        listview_city.adapter = adapter
    }


    private val handler = Handler()
    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private val delayRun = Runnable {
        searchCityData()
    }



    override fun click() {
        this.finish()
    }
}
