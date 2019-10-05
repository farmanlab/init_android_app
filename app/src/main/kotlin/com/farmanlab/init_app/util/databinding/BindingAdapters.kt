package com.farmanlab.init_app.util.databinding

import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import com.farmanlab.init_app.util.extensions.setTextOrGone

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("showOrHide")
fun ContentLoadingProgressBar.showOrHide(toShow: Boolean) {
    if (toShow) show() else hide()
}

@BindingAdapter("enableOrDisable")
fun View.enableOrDisable(isEnabled: Boolean) {
    this.isEnabled = isEnabled
}

@BindingAdapter("setTextOrGone")
fun TextView.setTextOrGoneAdapter(string: String?) {
    setTextOrGone(string)
}

@BindingAdapter("drawableStartFromResource")
fun drawableStartFromResource(textView: TextView, @DrawableRes res: Int) {
    val drawables = textView.compoundDrawablesRelative
    textView.setCompoundDrawablesRelative(
        ContextCompat.getDrawable(textView.context, res)?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        },
        drawables[1],
        drawables[2],
        drawables[3]
    )
}
