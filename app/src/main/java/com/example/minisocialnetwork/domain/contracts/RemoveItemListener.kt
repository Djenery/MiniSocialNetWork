package com.example.minisocialnetwork.domain.contracts

/**

Listener interface for handling item removal events in the ContactsAdapter.
 */
interface RemoveItemListener {

    /**
    Called when an item needs to be removed from the list.

    @param contact The Contact item to remove.
     */
    fun onRemoveItem(position: Int)
}