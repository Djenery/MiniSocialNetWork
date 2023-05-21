package com.example.minisocialnetwork.ui.contactsList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minisocialnetwork.datastore.FakeContacts
import com.example.minisocialnetwork.model.Contact

class MyContactsViewModel : ViewModel() {

    private val _contactsList =  MutableLiveData<List<Contact>>()
    val contactsList: LiveData<List<Contact>> = _contactsList

    init {
        _contactsList.value = FakeContacts.getContacts()
        Log.d("aaaa", "viewModel $contactsList")
    }





}