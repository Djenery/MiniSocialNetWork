package com.example.minisocialnetwork.domain.repository

/**

Listener interface for handling item removal events in the ContactsAdapter.
 */
interface ItemListener {

    /**
    Called when an item needs to be removed from the list.

    @param contact The Contact item to remove.
     */
    fun onRemoveItem(position: Int)
    fun onClickItem(position: Int)
}