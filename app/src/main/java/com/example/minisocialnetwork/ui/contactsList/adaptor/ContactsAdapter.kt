package com.example.minisocialnetwork.ui.contactsList.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.minisocialnetwork.databinding.ItemUserBinding
import com.example.minisocialnetwork.domain.contracts.RemoveItemListener
import com.example.minisocialnetwork.domain.model.Contact

class ContactsAdapter(private val listener: RemoveItemListener) :
    ListAdapter<Contact, ContactsViewHolder>(ContactsDiffUtil()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener
        )
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}