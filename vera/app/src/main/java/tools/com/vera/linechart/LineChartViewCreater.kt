package tools.com.vera.linechart

import android.app.Activity
import android.graphics.Color
import android.view.View
import lecho.lib.hellocharts.gesture.ContainerScrollType
import lecho.lib.hellocharts.gesture.ZoomType
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.view.LineChartView
import tools.com.vera.R

object LineChartViewCreater {

    private var lineChart: LineChartView? = null
    var weeks = arrayOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")//X轴的标注
    var weather = IntArray(3)//图表的数据
    private val mPointValues = ArrayList<PointValue>()
    private val mAxisValues = ArrayList<AxisValue>()


    fun showLineChartview(activity: Activity,weather : IntArray) {
        this.weather = weather
        lineChart = activity.findViewById(R.id.charter_line)
        if (weather.isEmpty()) {
            lineChart!!.visibility = View.GONE
        }else {
            lineChart!!.visibility = View.VISIBLE
        }
        getAxisLables()//获取x轴的标注
        getAxisPoints()//获取坐标点
        initLineChart()//初始化
    }


    /**
     * 初始化LineChart的一些设置
     */
    private fun initLineChart() {
        val line = Line(mPointValues).setColor(Color.WHITE).setCubic(false)  //折线的颜色
        val lines = ArrayList<Line>()
        line.setShape(ValueShape.CIRCLE)//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(false)//曲线是否平滑
        line.setFilled(true)//是否填充曲线的面积
        //      line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(true)//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true)//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true)//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line)
        val data = LineChartData()
        data.lines = lines

        //坐标轴
        val axisX = Axis() //X轴
        axisX.setHasTiltedLabels(true)
        axisX.setTextColor(Color.WHITE)  //设置字体颜色
//        axisX.setName("未来几天的天气")  //表格名称
//        axisX.setTextSize(15)//设置字体大小
        axisX.setMaxLabelChars(7)  //最多几个X轴坐标
        axisX.setValues(mAxisValues)  //填充X轴的坐标名称
        data.axisXBottom = axisX //x 轴在底部
        //      data.setAxisXTop(axisX);  //x 轴在顶部

        val axisY = Axis()  //Y轴
        axisY.setMaxLabelChars(7) //默认是3，只能看最后三个数字
        axisY.setName("温度")//y轴标注
        axisY.setTextSize(13)//设置字体大小

        data.axisYLeft = axisY  //Y轴设置在左边
//              data.setAxisYRight(axisY);  //y轴设置在右边

        //设置行为属性，支持缩放、滑动以及平移
        lineChart!!.setInteractive(true)
        lineChart!!.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL)
        lineChart!!.setContainerScrollEnabled(false, ContainerScrollType.HORIZONTAL)
        lineChart!!.setLineChartData(data)
        lineChart!!.setVisibility(View.VISIBLE)
    }


    /**
     * X 轴的显示
     */
    private fun getAxisLables() {
        for (i in 0 until weeks.size) {
            mAxisValues.add(AxisValue(i.toFloat()).setLabel(weeks[i]))
        }
    }

    /**
     * 图表的每个点的显示
     */
    private fun getAxisPoints() {
        for (i in 0 until weather.size) {
            mPointValues.add(PointValue(i.toFloat(), weather[i].toFloat()))
        }
    }


}