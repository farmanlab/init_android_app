package com.farmanlab.init_app.util.extensions

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView

fun TextView.setTextOrGone(string: String?) {
    if (string.isNullOrEmpty()) {
        visibility = View.GONE
    } else {
        visibility = View.VISIBLE
        this.text = string
    }
}

fun TextView.textFromHtml(string: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(string, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(string)
    }
}

val View.heightWithPadding: Int
    get() = height + paddingTop + paddingBottom
