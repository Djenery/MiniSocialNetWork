package com.example.minisocialnetwork.util.extentions

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Sets up a callback for item touch events in a RecyclerView.
 * @param callback The callback function to be invoked when an item is swiped.
 */


fun RecyclerView.onItemTouch(callback: (RecyclerView.ViewHolder) -> Unit) {
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false


        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            callback(viewHolder)
        }
    }).attachToRecyclerView(this)
}