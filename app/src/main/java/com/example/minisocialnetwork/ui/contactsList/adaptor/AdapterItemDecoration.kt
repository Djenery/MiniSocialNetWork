package com.example.minisocialnetwork.ui.contactsList.adaptor

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialnetwork.util.Constants.ITEM_VERTICAL_MARGIN


/**

Item decoration for a RecyclerView that adds vertical and top indentation to each item.

This class extends RecyclerView.ItemDecoration and overrides the getItemOffsets method

to apply vertical and top indentation to each item in the RecyclerView.
 */
class IndentItemDecoration :
    RecyclerView.ItemDecoration() {
    // TODO delete all double blank lines in project.


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