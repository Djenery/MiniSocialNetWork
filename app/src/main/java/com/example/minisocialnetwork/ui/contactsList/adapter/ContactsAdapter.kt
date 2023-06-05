package com.example.minisocialnetwork.ui.contactsList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.minisocialnetwork.databinding.ItemUserBinding
import com.example.minisocialnetwork.domain.contracts.RemoveItemListener
import com.example.minisocialnetwork.domain.model.Contact

/**

Adapter class for the Contacts RecyclerView.

This class extends the ListAdapter class and provides the necessary implementation

to bind the Contact items to the ContactsViewHolder and handle item removal through

the RemoveItemListener.

@param listener The listener for item removal events.
 */
class ContactsAdapter(private val listener: RemoveItemListener) :
    ListAdapter<Contact, ContactsViewHolder>(ContactsDiffUtil()) {

    /**

    Creates a new ContactsViewHolder and initializes it with the inflated view
    from the specified parent ViewGroup.

    @param parent The parent ViewGroup.

    @param viewType The view type of the new view.

    @return The created ContactsViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), listener
        )
    }

    /**

    Binds the Contact item at the specified position to the ContactsViewHolder.

    @param holder The ContactsViewHolder to bind the item to.

    @param position The position of the item in the list.
     */
    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.setListeners()
    }

}