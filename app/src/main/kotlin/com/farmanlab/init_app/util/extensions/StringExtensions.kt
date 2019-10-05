package com.farmanlab.init_app.util.extensions

import android.content.Context
import androidx.annotation.ColorRes
import java.text.Normalizer

private const val HIRA_TO_KANA_DELTA = 'ア' - 'あ'

fun String.hiraganaToKana(): String = map {
    if (Character.UnicodeBlock.of(it) == Character.UnicodeBlock.HIRAGANA) {
        it + HIRA_TO_KANA_DELTA
    } else {
        it
    }
}.joinToString("")

fun String.normalize(): String =
    Normalizer.normalize(hiraganaToKana().toLowerCase(), Normalizer.Form.NFKC).toString()

fun String.htmlColor(value: String, colorCode: String) =
    "$this<font color=\"#${colorCode.trimStart('#')}\"$value</font>"


fun String.htmlColor(value: String, @ColorRes colorRes: Int, context: Context): String =
    htmlColor(value, context.getColorString(colorRes))

fun String.toHtmlColor(colorCode: String) =
    "<font color=\"#${colorCode.trimStart('#')}\">$this</font>"


fun String.toHtmlColor(@ColorRes colorRes: Int, context: Context) =
    toHtmlColor(context.getColorString(colorRes))
