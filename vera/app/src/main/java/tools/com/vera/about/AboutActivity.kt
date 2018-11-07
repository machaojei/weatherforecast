package tools.com.vera.about

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_about_layout.*
import tools.com.vera.BuildConfig
import tools.com.vera.util.ContactUtil
import tools.com.vera.R

/**
 * @date: 18-9-6
 *
 * @author bazhuayu
 *
 * @description 关于
 */
class AboutActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_layout)


        tv_appversion.setText("当前版本："+BuildConfig.VERSION_NAME)

        var contacts = ArrayList<ContactVo> ()

        var contactVo1 = ContactVo(R.drawable.alipay,"捐赠开发者", ContactUtil.ali)
        var contactVo2 = ContactVo(R.drawable.qq, ContactUtil.qqname, ContactUtil.qq)
        var contactVo3 = ContactVo(R.drawable.weixin, ContactUtil.weixinname, ContactUtil.weixin)
        var contactVo4 = ContactVo(R.drawable.weibo, ContactUtil.weiboname, ContactUtil.weibo)
        var contactVo5 = ContactVo(R.drawable.mail, ContactUtil.email, ContactUtil.mail)

        contacts.add(contactVo1)
        contacts.add(contactVo2)
        contacts.add(contactVo3)
        contacts.add(contactVo4)
        contacts.add(contactVo5)

        var adapter =  ContactAdapter(this,contacts)

        val layoutManager = LinearLayoutManager(this)
        list_contact.layoutManager = layoutManager
        list_contact.adapter = adapter


    }





}