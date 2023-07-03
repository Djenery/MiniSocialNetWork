package com.example.minisocialnetwork.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minisocialnetwork.data.FakeContacts
import com.example.minisocialnetwork.domain.model.Contact
import com.example.minisocialnetwork.util.AutoIdIncrement

/**
 * ViewModel class for managing the contacts list.
 */
class MyContactsViewModel : ViewModel() {

    private val _contactsList = MutableLiveData<List<Contact>>()
    val contactsList: LiveData<List<Contact>> = _contactsList

    private val positions = mutableListOf<Pair<Int, Contact>>()

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
     * @param item The contact to be deleted.
     */
    fun deleteContact(item: Contact) {
        positions.clear()
        _contactsList.value = _contactsList.value?.toMutableList()?.apply {
            positions.add(indexOf(item) to item)
            remove(item)
        }

    }

    /**
     * Inserts a contact at a specific position in the contacts list.
     * @return position of inserted element
     */
    fun insertAt(): Int {
        val position = positions.first().first
        _contactsList.value = contactsList.value?.toMutableList()?.apply {
            add(position, requireNotNull(positions.first().second))
        }
        return position
    }

    private val _multiSelectedState = MutableLiveData<Boolean>()
    val multiSelectedState: LiveData<Boolean> = _multiSelectedState

    private val _selectedItems = MutableLiveData<List<Contact>>(emptyList())
    val selectedItems: LiveData<List<Contact>> = _selectedItems

    fun addSelectedItem(item: Contact) {
        if (_selectedItems.value.isNullOrEmpty()) {
            positions.clear()
            _multiSelectedState.value = true
        }
        val currentItemPosition = _contactsList.value!!.indexOf(item)
        Log.d("MyContactsViewModel", "${item.id}")
        Log.d("MyContactsViewModel", "${_multiSelectedState.value}")
        _selectedItems.value = _selectedItems.value?.toMutableList()?.apply {
            positions.add(currentItemPosition to item)
            add(item)
        }
    }

    fun removeSelectedItem(item: Contact) {
        _selectedItems.value = _selectedItems.value?.toMutableList()?.apply {
            positions.removeIf { it.second == item }
            removeIf { it == item }
        }
        if (_selectedItems.value.isNullOrEmpty()) {
            _multiSelectedState.value = false
        }
    }

    fun deleteAllSelectedItems() {
        val currentItems = _contactsList.value ?: return
        val selectedItems = _selectedItems.value ?: return
        val updatedItems = currentItems.filter { item ->
            !selectedItems.contains(item)
        }
        _contactsList.value = updatedItems
        _selectedItems.value = emptyList()
        _multiSelectedState.value = false
    }

    fun insertMultipleItems() {
        positions.forEach {
            _contactsList.value = contactsList.value?.toMutableList()?.apply {
                add(it.first, requireNotNull(it.second))
            }
        }

    }
}