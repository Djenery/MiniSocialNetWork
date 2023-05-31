package com.example.minisocialnetwork.ui.contactsList.adaptor

import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialnetwork.databinding.ItemUserBinding
import com.example.minisocialnetwork.domain.contracts.RemoveItemListener
import com.example.minisocialnetwork.domain.model.Contact
import com.example.minisocialnetwork.util.extentions.urlLoader

/**
 * ViewHolder class for displaying Contact items in a RecyclerView.
 * @param binding The data binding object for the item view.
 * @param listener The listener for item removal events.
 */
class ContactsViewHolder(
    private val binding: ItemUserBinding,
    private val listener: RemoveItemListener
) : RecyclerView.ViewHolder(binding.root) {


    /**
     * Binds the data of a Contact item to the item view.
     * @param item The Contact item to bind.
     */
    fun bind(item: Contact) {
        with(binding) {
            tvItemUserUserName.text = item.name
            tvItemUserProfession.text = item.profession
            ivItemUserUserIcon.urlLoader(item.photo)
        }
    }


    /**
     * Sets the click listener for the user item view.
     * The listener triggers the removal of the item at the current adapter position.
     */
    fun setListeners() {
        binding.userItem.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onRemoveItem(position)
            }

        }
    }

}

