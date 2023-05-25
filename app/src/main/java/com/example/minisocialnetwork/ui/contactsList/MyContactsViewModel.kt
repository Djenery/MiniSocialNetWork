package com.example.minisocialnetwork.ui.contactsList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minisocialnetwork.datastore.FakeContacts
import com.example.minisocialnetwork.domain.model.Contact
import com.example.minisocialnetwork.util.AutoIdIncrement

class MyContactsViewModel : ViewModel() {


    private val _contactsList = MutableLiveData<List<Contact>>()
    val contactsList: LiveData<List<Contact>> = _contactsList

    init {
        _contactsList.value = FakeContacts.getContacts()
        Log.d("aaaa", "viewModel $contactsList")
    }


    fun addContact(name: String, profession: String) {
        _contactsList.value = contactsList.value?.toMutableList()?.apply {
            add(Contact(AutoIdIncrement.getId(), FakeContacts.getRandomPhoto(), name, profession))
        }
        Log.d("aaaa", "contact added $contactsList")
    }


    fun deleteContact(contact: Contact) {
        _contactsList.value = contactsList.value?.toMutableList()?.apply {
            remove(contact)
        }
    }

    fun insertAt(contact: Contact, position: Int) {
        _contactsList.value = contactsList.value?.toMutableList()?.apply {
            add(position, contact)
        }

    }


}