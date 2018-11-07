package tools.com.vera.util

import org.dom4j.DocumentHelper
import org.dom4j.Element
import tools.com.vera.citymanage.data.CityVo


/**
 * @date: 18-9-7
 *
 * @author bazhuayu
 *
 * @description
 */
object XMLUtils {

    public fun readXML(XMLPathAndName: String): List<CityVo> {
        var t = CityVo("", "", "", "")

        val list = ArrayList<CityVo>()//创建list集合
        try {
            // 获取实体类的所有属性，返回Field数组
            val field = t::class.java.getDeclaredFields()
            //将字符串转化为xml
            val doc = DocumentHelper.parseText(XMLPathAndName)
            //获得根节点
            val node = doc.getRootElement()
            val it = node.elementIterator()
            var e: Element
            // 遍历
            while (it.hasNext()) {
                // 获取某个子节点对象
                e = it.next()
                //获取实体类实例
                t = t::class.java.newInstance() as CityVo

                for (j in field.indices) {
                    // 获取属性的名字
                    var name = field[j].getName()
                    //将第一位转化为大写，便于获取实体类中set,get方法
                    name = name.substring(0, 1).toUpperCase() + name.substring(1)
                    //获得set方法
                    val m = t::class.java.getMethod("set$name", String::class.java)
                    //e.elementText元素的某个指定的子元素中的text信息
                    m.invoke(t, e.elementText(field[j].getName().toUpperCase()))
                }
                list.add(t)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list
    }

}
