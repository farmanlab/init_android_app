package com.farmanlab.init_app.widget.bottomsheetbehavior

import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun <V : View> BottomSheetDialog.setBottomSheetBehavior(behavior: BottomSheetBehavior<V>) {
    val frameLayout =
        findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            ?: return
    (frameLayout.layoutParams as? CoordinatorLayout.LayoutParams)?.behavior = behavior
}

fun <V : View> BottomSheetDialog.getBottomSheetBehavior(): BottomSheetBehavior<*>? {
    val frameLayout =
        findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            ?: return null
    return (frameLayout.layoutParams as? CoordinatorLayout.LayoutParams)?.behavior as? BottomSheetBehavior<*>
}

