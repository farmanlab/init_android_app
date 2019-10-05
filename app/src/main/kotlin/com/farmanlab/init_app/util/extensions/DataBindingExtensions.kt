package com.farmanlab.init_app.util.extensions

import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding

inline fun <T : ViewDataBinding> T.executeAfter(block: T.() -> Unit) {
    block()
    executePendingBindings()
}

fun <T: ViewDataBinding> T.getString(@StringRes id: Int): String = root.context.getString(id)
