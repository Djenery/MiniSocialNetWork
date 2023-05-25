package com.example.minisocialnetwork.ui.contactsList.adaptor

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialnetwork.util.Constants.ITEM_VERTICAL_MARGIN

class IndentItemDecoration :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val density = Resources.getSystem().displayMetrics.density
        val indentSizePixels = (ITEM_VERTICAL_MARGIN * density).toInt()
        outRect.top = indentSizePixels
    }
}