package com.farmanlab.init_app.util.extensions

import android.content.ClipData
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop

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

fun View.startDragAndDropCompat(
    clipData: ClipData?,
    shadowBuilder: View.DragShadowBuilder,
    myLocalState: Any? = null,
    flags: Int
): Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    startDragAndDrop(clipData, shadowBuilder, myLocalState, flags)
} else {
    startDrag(clipData, shadowBuilder, myLocalState, flags)
}

val View.widthWithPadding
    get() = width + paddingStart + paddingEnd
val View.measuredWidthWithPadding
    get() = measuredWidth + paddingStart + paddingEnd
val View.heightWithPadding
    get() = height + paddingTop + paddingBottom
val View.measuredHeightWithPadding
    get() = measuredHeight + paddingTop + paddingBottom
val View.widthWithMargin
    get() = width + marginStart + marginEnd
val View.measuredWidthWithMargin
    get() = measuredWidth + marginStart + marginEnd
val View.heightWithMargin
    get() = height + marginTop + marginBottom
val View.measuredHeightWithMargin
    get() = measuredHeight + marginTop + marginBottom
