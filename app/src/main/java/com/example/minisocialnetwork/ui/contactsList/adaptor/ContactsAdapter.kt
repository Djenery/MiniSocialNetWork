package com.example.minisocialnetwork.ui.contactsList.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.minisocialnetwork.databinding.ItemUserBinding
import com.example.minisocialnetwork.model.Contact

class ContactsAdapter :
    ListAdapter<Contact, ContactsViewHolder>(ContactsDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}