package com.farmanlab.init_app.util.extensions

import android.content.Context

fun Context.getColorString(res: Int): String = getString(res).let {
    if (it.length == 9) {
        // #AARRGGBB pattern.
        it.removeRange(1..2)
    } else {
        it
    }
}
