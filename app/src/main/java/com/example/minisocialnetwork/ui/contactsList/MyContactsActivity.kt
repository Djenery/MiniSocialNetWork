package com.example.minisocialnetwork.ui.contactsList

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minisocialnetwork.databinding.ActivityMyContactsBinding
import com.example.minisocialnetwork.domain.contracts.AddContactListener
import com.example.minisocialnetwork.domain.contracts.RemoveItemListener
import com.example.minisocialnetwork.ui.contactsList.adaptor.ContactsAdapter
import com.example.minisocialnetwork.ui.contactsList.adaptor.IndentItemDecoration
import com.example.minisocialnetwork.util.Constants.UNDO
import com.example.minisocialnetwork.util.extentions.onItemTouch
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class MyContactsActivity : AppCompatActivity(), AddContactListener, RemoveItemListener {
    private lateinit var binding: ActivityMyContactsBinding
    private val adapter: ContactsAdapter by lazy {
        ContactsAdapter(this)
    }

    private val viewModel: MyContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        setObservers()
        setListeners()

    }


    private fun setListeners() {
        binding.tvAddContactMyContacts.setOnClickListener {
            AddContactDialogFragment().show(supportFragmentManager, AddContactDialogFragment.TAG)
        }
    }


    private fun initRecyclerView() {
        with(binding) {
            recyclerViewMyContacts.layoutManager = LinearLayoutManager(this@MyContactsActivity)
            recyclerViewMyContacts.adapter = adapter
            recyclerViewMyContacts.addItemDecoration(IndentItemDecoration())
            recyclerViewMyContacts.onItemTouch { item ->
                val position = item.adapterPosition
                onRemoveItem(position)
            }
        }
    }

    override fun onRemoveItem(position: Int) {
        val contact = adapter.currentList[position]
        val listPosition = adapter.currentList.indexOf(contact)
        viewModel.deleteContact(contact)
        Snackbar.make(
            binding.recyclerViewMyContacts, listPosition.toString(),
            Snackbar.LENGTH_LONG
        ).setAction(UNDO) { _: View? ->
            viewModel.insertAt(contact, position)
        }.show()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.contactsList.observe(this@MyContactsActivity) { contacts ->
                Log.d("aaaa", "contacts $contacts")
                adapter.submitList(contacts.toMutableList())
            }
        }
    }

    override fun onAddContact(name: String, profession: String) {
        viewModel.addContact(name, profession)
    }


}
