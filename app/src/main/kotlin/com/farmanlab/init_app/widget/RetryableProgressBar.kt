package com.farmanlab.init_app.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.farmanlab.init_app.R
import kotlinx.android.synthetic.main.view_retryable_progress_bar.view.*

class RetryableProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val loadingProgressBar: ProgressBar
    val errorText: TextView
    val errorIcon: ImageView
    val retryButton: Button

    private var retry: () -> Unit = {}

    var state: RetryableProgressBarState = RetryableProgressBarState.Loading
        set(value) {
            field = value
            when (value) {
                is RetryableProgressBarState.Loading -> {
                    loadingProgressBar.visibility = View.VISIBLE
                    loadingProgressBar.error_view_group.visibility = View.GONE
                }
                is RetryableProgressBarState.Succeeded -> {
                    visibility = View.GONE
                }
                is RetryableProgressBarState.Failed -> {
                    loadingProgressBar.visibility = View.GONE
                    loadingProgressBar.error_view_group.visibility = View.VISIBLE
                    retry = value.retryFunction
                }
            }
        }

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.RetryableProgressBar)
        val errorTextValue = arr.getString(R.styleable.RetryableProgressBar_errorText)
        val errorIconDrawable = arr.getDrawable(R.styleable.RetryableProgressBar_errorIconDrawable)
        val retryTextValue = arr.getString(R.styleable.RetryableProgressBar_retryText)
        arr.recycle()
        inflate(context, R.layout.view_retryable_progress_bar, this)
        loadingProgressBar = progress_bar
        errorText = error_text
        errorIcon = error_icon
        retryButton = retry_button
        errorTextValue?.let { errorText.text = errorTextValue }
        errorIconDrawable?.let { errorIcon.setImageDrawable(errorIconDrawable) }
        retryTextValue?.let { retryButton.text = retryTextValue }
        retryButton.setOnClickListener { retry() }
    }
}

sealed class RetryableProgressBarState {
    object Loading : RetryableProgressBarState()
    object Succeeded : RetryableProgressBarState()
    data class Failed(val retryFunction: () -> Unit) : RetryableProgressBarState()
}
