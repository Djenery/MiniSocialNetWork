package com.example.minisocialnetwork.ui.contactsList.adaptor

import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialnetwork.databinding.ItemUserBinding
import com.example.minisocialnetwork.model.Contact
import com.example.minisocialnetwork.util.ImageLoader.urlLoader

class ContactsViewHolder(
    private val binding: ItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Contact) {
        with(binding) {
            tvItemUserUserName.text = item.name
            tvItemUserProfession.text = item.profession
            ivItemUserUserIcon.urlLoader(item.photo)
        }
    }

}

