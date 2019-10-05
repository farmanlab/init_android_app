package com.farmanlab.init_app.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class EasyDividerItemDecoration(
    context: Context,
    private val drawable: Drawable,
    private val orientation: Int = RecyclerView.VERTICAL,
    private val showBeforeFirstItem: Boolean = false,
    private val showAfterLastItem: Boolean = false,
    private val shouldDrawSkipFilter: ((RecyclerView, View) -> Boolean)? = null
) : DividerItemDecoration(context, orientation) {

    constructor(
        context: Context,
        drawableResId: Int,
        orientation: Int = RecyclerView.VERTICAL,
        showBeforeFirstItem: Boolean = false,
        showAfterLastItem: Boolean = false,
        shouldDrawSkipFilter: ((RecyclerView, View) -> Boolean)? = null
    ) : this(
        context,
        requireNotNull(
            ContextCompat.getDrawable(context, drawableResId)
        ),
        orientation,
        showBeforeFirstItem,
        showAfterLastItem,
        shouldDrawSkipFilter
    )

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        when (orientation) {
            LinearLayoutManager.VERTICAL -> {
                drawVertical(canvas, parent)
            }
            LinearLayoutManager.HORIZONTAL -> {
                drawHorizontal(canvas, parent)
            }
            else -> throw error(
                "Invalid orientation value." +
                        " must be set LinearLayoutManager.VERTICAL" +
                        " or LinearLayoutManager.HORIZONTAL."
            )
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (!showBeforeFirstItem && parent.getChildAdapterPosition(view) == 0) {
            outRect.set(0, 0, 0, 0)
            return
        }
        if (shouldDrawSkipFilter?.invoke(parent, view) == true) {
            outRect.set(0, 0, 0, 0)
            return
        }
        if (orientation == VERTICAL) {
            outRect.set(0, 0, 0, drawable.intrinsicHeight)
        } else {
            outRect.set(0, 0, drawable.intrinsicWidth, 0)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        val childCount = parent.childCount
        getDrawDividerIndexRange(childCount).forEach { i ->
            val child = parent.getChildAt(i)
            if (shouldDrawSkipFilter?.invoke(parent, child) == true) return@forEach

            val bounds = Rect().also { parent.getDecoratedBoundsWithMargins(child, it) }
            val bottom = bounds.bottom + child.translationY.roundToInt()
            val top = bottom - drawable.intrinsicHeight
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(canvas)
        }

        if (showBeforeFirstItem && childCount > 0) {
            val child = parent.getChildAt(0)
            val bounds = Rect().also { parent.getDecoratedBoundsWithMargins(child, it) }
            val top = bounds.top + child.translationY.roundToInt()
            val bottom = top + drawable.intrinsicHeight
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int

        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, top, parent.width - parent.paddingRight, bottom)
        } else {
            top = 0
            bottom = parent.height
        }

        val childCount = parent.childCount
        getDrawDividerIndexRange(childCount).forEach { i ->
            val child = parent.getChildAt(i)
            if (shouldDrawSkipFilter?.invoke(parent, child) == true) return@forEach

            val bounds = Rect().also { parent.getDecoratedBoundsWithMargins(child, it) }
            val right = bounds.right + child.translationX.roundToInt()
            val left = right - drawable.intrinsicWidth
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(canvas)
        }

        if (showBeforeFirstItem && childCount > 0) {
            val child = parent.getChildAt(0)
            val bounds = Rect().also { parent.getDecoratedBoundsWithMargins(child, it) }
            val left = bounds.left + child.translationX.roundToInt()
            val right = left + drawable.intrinsicWidth
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(canvas)
        }
        canvas.restore()
    }

    private fun getDrawDividerIndexRange(childCount: Int): IntRange {
        val end = if (showAfterLastItem) childCount - 1 else childCount - 2
        return IntRange(0, end)
    }
}
