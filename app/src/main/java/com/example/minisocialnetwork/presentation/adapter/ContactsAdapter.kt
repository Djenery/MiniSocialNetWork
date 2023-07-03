package com.example.minisocialnetwork.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialnetwork.databinding.ItemUserBinding
import com.example.minisocialnetwork.databinding.ItemUserCheckboxBinding
import com.example.minisocialnetwork.domain.model.Contact
import com.example.minisocialnetwork.domain.repository.ItemListener
import com.example.minisocialnetwork.domain.repository.MultiSelectListener
import com.example.minisocialnetwork.presentation.adapter.viewHolders.ContactsMultiSelectionViewHolder
import com.example.minisocialnetwork.presentation.adapter.viewHolders.ContactsViewHolder


class ContactsAdapter(
    private val itemListener: ItemListener,
    private val multiselectListener: MultiSelectListener
) :
    ListAdapter<Contact, RecyclerView.ViewHolder>(ContactsDiffUtil()) {
    private var isMultiselectEnabled = false
    private var selectedItems = listOf<Contact>()

    private enum class ViewType {
        Normal, Multiselect
    }

    override fun getItemViewType(position: Int): Int {
        return if (isMultiselectEnabled) ViewType.Multiselect.ordinal else ViewType.Normal.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isMultiselectEnabled) {
            ContactsMultiSelectionViewHolder(
                ItemUserCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                ::onMultiselectItemStateChange
            )
        } else {
            ContactsViewHolder(
                ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                itemListener,
                multiselectListener
            )
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeMultiselectState(isMultiselectEnabled: Boolean) {
        this.isMultiselectEnabled = isMultiselectEnabled
        notifyDataSetChanged()
    }

    fun changeMultiselectItems(selectedItems: List<Contact>) {
        this.selectedItems = selectedItems
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContactsViewHolder -> holder.bind(getItem(position))
            is ContactsMultiSelectionViewHolder -> holder.bind(
                getItem(position),
                selectedItems.contains(getItem(position))
            )
        }
    }

    private fun onMultiselectItemStateChange(isChecked: Boolean, item: Contact) {
        if (isChecked) {
            multiselectListener.addItemToSelectedState(item)
        } else {
            multiselectListener.removeItemFromSelectedState(item)
        }
    }
}

