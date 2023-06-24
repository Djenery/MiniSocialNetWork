package com.example.minisocialnetwork.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minisocialnetwork.data.FakeContacts
import com.example.minisocialnetwork.domain.model.Contact
import com.example.minisocialnetwork.util.AutoIdIncrement
import kotlin.properties.Delegates

/**
 * ViewModel class for managing the contacts list.
 */
class MyContactsViewModel : ViewModel() {

    private val _contactsList = MutableLiveData<List<Contact>>()
    val contactsList: LiveData<List<Contact>> = _contactsList

    private var position by Delegates.notNull<Int>()
    private var contact: Contact? = null

    /**
     * Initializes the ViewModel with a list of fake contacts.
     */
    init {
        _contactsList.value = FakeContacts.getContacts()
    }

    /**
     * Adds a new contact to the contacts list.
     *
     * @param name The name of the contact.
     * @param profession The profession of the contact.
     */
    fun addContact(name: String, profession: String) {
        _contactsList.value = contactsList.value?.toMutableList()?.apply {
            add(Contact(AutoIdIncrement.getId(), FakeContacts.getRandomPhoto(), name, profession))
        }
    }

    /**
     * Deletes a contact from the contacts list.
     *
     * @param contact The contact to be deleted.
     */
    fun deleteContact(contact: Contact) {
        _contactsList.value = contactsList.value?.toMutableList()?.apply {
            position = indexOf(contact)
            this@MyContactsViewModel.contact = contact
            remove(contact)
        }

    }

    /**
     * Inserts a contact at a specific position in the contacts list.
     * @return position of inserted element
     */
    fun insertAt(): Int {
        _contactsList.value = contactsList.value?.toMutableList()?.apply {
            add(position, requireNotNull(contact))
        }
        return position
    }
}