package tools.com.vera.about

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.canking.minipay.Config
import com.canking.minipay.MiniPayUtils
import tools.com.vera.R
import tools.com.vera.util.ContactUtil


/**
 * @date: 18-9-6
 *
 * @author bazhuayu
 *
 * @description
 */
class ContactAdapter(private val context: Context, val list: ArrayList<ContactVo>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_recycler, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ServiceCast")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is RecyclerViewHolder) {
            holder.imageView.setImageDrawable(context.getDrawable(list[position].image))
            holder.textView.setText(list[position].text)

            holder.textView.setOnLongClickListener {

                var clipmanage = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("", holder.textView.text)
                clipmanage.primaryClip = clip
                Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show()
                return@setOnLongClickListener true

            }

            holder.textView.setOnClickListener {
                when (list[position].id) {
                    ContactUtil.ali -> donateAlipay("a6x07038yny2jbeej0wig43")// 跳转捐赠画面

                    ContactUtil.weibo -> gotoWeibo()

                }

            }
        }

    }

    inner class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.tv_text)

        var imageView: ImageView = view.findViewById(R.id.img)
    }

    /**
     * 跳转微博主页
     * */
    fun gotoWeibo() {
        var intent = Intent()
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("sinaweibo://userinfo?nick=" + ContactUtil.weiboname))
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "您还没有安装微博！", Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * 支付宝支付
     * @param payCode 收款码后面的字符串；例如：收款二维码里面的字符串为 https://qr.alipay.com/stx00187oxldjvyo3ofaw60 ，则
     * payCode = stx00187oxldjvyo3ofaw60
     * 注：不区分大小写
     */
    private fun donateAlipay(payCode: String) {

        MiniPayUtils.setupPay(context, Config.Builder(payCode, R.mipmap.ali, R.mipmap.weixinpay).build())
    }

}
