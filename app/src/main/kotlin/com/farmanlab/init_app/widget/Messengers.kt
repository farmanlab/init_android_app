package com.farmanlab.init_app.widget

import com.farmanlab.init_app.util.StringHolder
import com.google.android.material.snackbar.Snackbar

sealed class Messenger {
    abstract val text: StringHolder

    data class Toast(
        override val text: StringHolder,
        val duration: Int = android.widget.Toast.LENGTH_SHORT
    ) : Messenger()

    data class SnackBar(
        override val text: StringHolder,
        val duration: Int = Snackbar.LENGTH_SHORT,
        val retryAction: (() -> Unit)? = null
    ) : Messenger()
}
