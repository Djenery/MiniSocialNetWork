package com.example.minisocialnetwork.ui.contactsList

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.ActivityMyContactsBinding
import com.example.minisocialnetwork.domain.contracts.AddContactListener
import com.example.minisocialnetwork.domain.contracts.RemoveItemListener
import com.example.minisocialnetwork.domain.model.Contact
import com.example.minisocialnetwork.ui.contactsList.adapter.ContactsAdapter
import com.example.minisocialnetwork.ui.contactsList.adapter.IndentItemDecoration
import com.example.minisocialnetwork.util.Constants.UNDO
import com.example.minisocialnetwork.util.extentions.onItemTouch
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

/**
 * The main activity for managing contacts.
 */
class MyContactsActivity : AppCompatActivity(), AddContactListener, RemoveItemListener {
    private lateinit var binding: ActivityMyContactsBinding
    private val contactsAdapter: ContactsAdapter by lazy {
        ContactsAdapter(this)
    }

    private val viewModel: MyContactsViewModel by viewModels()

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized
     * after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        setObservers()
        setListeners()
    }

    /**
     * Initializes the RecyclerView for displaying contacts.
     */
    private fun initRecyclerView() {
        with(binding.recyclerViewMyContacts) {
            layoutManager = LinearLayoutManager(this@MyContactsActivity)
            adapter = contactsAdapter
            addItemDecoration(IndentItemDecoration())
            onItemTouch { item ->
                val position = item.adapterPosition
                contactsAdapter.currentList.getOrNull(position)?.let {
                    onRemoveItem(position, it)
                }
            }
        }
    }

    /**
     * Called when an item is removed from the RecyclerView.
     *
     * @param position The position of the removed item.
     */
    override fun onRemoveItem(position: Int, contact: Contact) {
        viewModel.deleteContact(contact)
        Snackbar.make(
            binding.recyclerViewMyContacts, position.toString(),
            Snackbar.LENGTH_LONG
        ).setAction(UNDO) { _: View? ->
            viewModel.insertAt(contact, position)
        }.show()
    }

    /**
     * Sets up observers for the ViewModel's contacts list.
     */
    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.contactsList.observe(this@MyContactsActivity) { contacts ->
                contactsAdapter.submitList(contacts.toMutableList())
            }
        }
    }

    /**
     * Sets up listeners for UI elements.
     */
    private fun setListeners() {
        binding.tvAddContactMyContacts.setOnClickListener {
            AddContactDialogFragment().show(supportFragmentManager, AddContactDialogFragment.TAG)
        }
        binding.viewMyContactsArrowBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    /**
     * Called when a contact is added through the AddContactDialogFragment.
     *
     * @param name The name of the contact.
     * @param profession The profession of the contact.
     */
    override fun onAddContact(name: String, profession: String) {
        viewModel.addContact(name, profession)
    }
}
