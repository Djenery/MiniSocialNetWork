package com.example.minisocialnetwork.ui.contactsList.adaptor

import androidx.recyclerview.widget.DiffUtil
import com.example.minisocialnetwork.domain.model.Contact

class ContactsDiffUtil : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

}