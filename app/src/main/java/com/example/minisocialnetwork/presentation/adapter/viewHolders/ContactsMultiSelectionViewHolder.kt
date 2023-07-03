package com.example.minisocialnetwork.presentation.adapter.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialnetwork.databinding.ItemUserCheckboxBinding
import com.example.minisocialnetwork.domain.model.Contact
import com.example.minisocialnetwork.util.extentions.urlLoader


class ContactsMultiSelectionViewHolder(
    private val binding: ItemUserCheckboxBinding,
    private val onMultiselectItemStateChange: (Boolean, Contact) -> Unit
) : RecyclerView.ViewHolder(binding.root) {


    /**
     * Binds the data of a Contact item to the item view.
     * @param item The Contact item to bind.
     */
    fun bind(item: Contact, isChecked: Boolean) {
        with(binding) {
            tvItemUserUserName.text = item.name
            tvItemUserProfession.text = item.profession
            ivItemUserUserIcon.urlLoader(item.photo)
            cbItemUser.isChecked = isChecked

        }
        setListeners(item)
    }


    /**
     * Sets the click listener for the user item view.
     * The listener triggers the removal of the item at the current adapter position.
     */
    private fun setListeners(item: Contact) {
        with(binding) {
            itemUserCheckbox.setOnClickListener {
                val checked = !cbItemUser.isChecked
                cbItemUser.isChecked = checked
                onMultiselectItemStateChange(checked, item)
            }

        }
    }
}



