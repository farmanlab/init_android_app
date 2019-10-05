package com.farmanlab.init_app.util

import android.content.Context
import androidx.annotation.StringRes

sealed class StringHolder {

    open fun getString(context: Context): String = when (this) {
        is Row -> string
        is Resource -> getString(context)
    }

    data class Row(val string: String) : StringHolder()
    class Resource(@StringRes private val id: Int, private vararg val format: Any) :
        StringHolder() {
        override fun getString(context: Context): String = if (format.isEmpty()) {
            context.getString(id)
        } else {
            context.getString(id, format)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Resource

            if (id != other.id) return false
            if (!format.contentEquals(other.format)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + format.contentHashCode()
            return result
        }
    }
}
