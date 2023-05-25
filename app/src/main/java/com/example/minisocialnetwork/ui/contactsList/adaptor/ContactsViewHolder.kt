package com.example.minisocialnetwork.ui.contactsList.adaptor

import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialnetwork.databinding.ItemUserBinding
import com.example.minisocialnetwork.domain.contracts.RemoveItemListener
import com.example.minisocialnetwork.domain.model.Contact
import com.example.minisocialnetwork.util.extentions.urlLoader

class ContactsViewHolder(
    private val binding: ItemUserBinding, private val listener: RemoveItemListener
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: Contact) {
        with(binding) {
            tvItemUserUserName.text = item.name
            tvItemUserProfession.text = item.profession
            ivItemUserUserIcon.urlLoader(item.photo)

            root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onRemoveItem(position)
                }

            }
        }
    }


}

