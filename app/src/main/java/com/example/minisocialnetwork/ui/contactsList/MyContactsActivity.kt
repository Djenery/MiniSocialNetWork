package com.example.minisocialnetwork.ui.contactsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minisocialnetwork.databinding.ActivityMyContactsBinding
import com.example.minisocialnetwork.datastore.FakeContacts
import com.example.minisocialnetwork.ui.contactsList.adaptor.ContactsAdapter
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MyContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyContactsBinding
    private val adapter: ContactsAdapter by lazy {
        ContactsAdapter()
    }

    private val viewModel: MyContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        setObservers()


    }

    private fun initRecyclerView() {


        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMyContacts.layoutManager = layoutManager
        binding.recyclerViewMyContacts.adapter = adapter
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.contactsList.observe(this@MyContactsActivity) { contacts ->
                Log.d("aaaa", "contacts $contacts")
                adapter.submitList(contacts.toMutableList())
            }
        }
    }


}