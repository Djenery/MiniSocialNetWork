package com.example.minisocialnetwork.presentation.contactsList.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.minisocialnetwork.domain.model.Contact

/**
 * DiffUtil callback for calculating the differences between two lists of Contact items.
 */
class ContactsDiffUtil : DiffUtil.ItemCallback<Contact>() {

    /**
     * Checks if the two Contact items have the same identifier.
     * @param oldItem The old Contact item.
     * @param newItem The new Contact item.
     * @return True if the items have the same identifier, false otherwise.
     */
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Checks if the content of the two Contact items is the same.
     * @param oldItem The old Contact item.
     * @param newItem The new Contact item.
     * @return True if the items have the same content, false otherwise.
     */
    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

}