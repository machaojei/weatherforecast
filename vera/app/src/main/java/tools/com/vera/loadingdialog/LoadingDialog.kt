package tools.com.vera.loadingdialog

import android.app.Dialog
import android.content.Context
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.loading_dialog_layout.*
import tools.com.vera.R

class LoadingDialog(context: Context?) : Dialog(context, R.style.dialog) {


    init {

        setContentView(R.layout.loading_dialog_layout)
        loading_image.startAnimation(AnimationUtils.loadAnimation(context, R.anim.progress_anim))
    }


}