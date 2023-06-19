package com.example.minisocialnetwork.presentation.contactsList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.FragmentMyContactsBinding
import com.example.minisocialnetwork.domain.repository.AddContactListener
import com.example.minisocialnetwork.domain.repository.ItemListener
import com.example.minisocialnetwork.presentation.base.BaseFragment
import com.example.minisocialnetwork.presentation.contactsList.adapter.ContactsAdapter
import com.example.minisocialnetwork.presentation.contactsList.adapter.IndentItemDecoration
import com.example.minisocialnetwork.presentation.detail.DetailViewFragment
import com.example.minisocialnetwork.util.Flag.NAV_GRAPH
import com.example.minisocialnetwork.util.extentions.onItemTouch
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MyContactsFragment :
    BaseFragment<FragmentMyContactsBinding>(FragmentMyContactsBinding::inflate),
    AddContactListener {

    private val mViewModel: MyContactsViewModel by viewModels()

    private val adapter: ContactsAdapter by lazy {
        ContactsAdapter(object : ItemListener {
            override fun onRemoveItem(position: Int) {
                removeItem(position)
            }

            override fun onClickItem(position: Int) {
                clickItem(position)
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setObservers()
        setListeners()

    }

    /**
     * Initializes the RecyclerView for displaying contacts.
     */
    private fun initRecyclerView() {
        with(binding) {
            recyclerViewMyContacts.layoutManager =
                LinearLayoutManager(requireContext())
            recyclerViewMyContacts.adapter = adapter
            recyclerViewMyContacts.addItemDecoration(IndentItemDecoration())
            recyclerViewMyContacts.onItemTouch {
                removeItem(it.adapterPosition)
            }
        }
    }


    /**
     * Called when an item is removed from the RecyclerView.
     *
     * @param position The position of the removed item.
     */
    private fun removeItem(position: Int) {
        val contact = adapter.currentList[position]
        mViewModel.deleteContact(contact)
        showSnackBar()

    }

    private fun showSnackBar() {
        Snackbar.make(
            binding.recyclerViewMyContacts, getString(R.string.contact_was_deleted),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.undo)) {
            binding.recyclerViewMyContacts.smoothScrollToPosition(mViewModel.insertAt())
        }.show()
    }

    private fun clickItem(position: Int) {
        val contact = adapter.currentList[position]
        if (NAV_GRAPH) {
            val action =
                MyContactsFragmentDirections.actionMyContactsFragmentToDetailViewFragment2(contact)
            findNavController().navigate(action)
        } else {
            val fragment =
                DetailViewFragment.newInstance(
                    contact.name,
                    contact.photo,
                    contact.profession
                )
            parentFragmentManager.commit {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
            }
        }

    }

    /**
     * Sets up observers for the ViewModel's contacts list.
     */
    private fun setObservers() {
        lifecycleScope.launch {
            mViewModel.contactsList.observe(viewLifecycleOwner) { contacts ->
                adapter.submitList(contacts.toMutableList())
            }
        }
    }

    /**
     * Sets up listeners for UI elements.
     */
    private fun setListeners() {
        with(binding) {
            tvAddContactMyContacts.setOnClickListener {
                AddContactDialogFragment().show(
                    childFragmentManager,
                    AddContactDialogFragment.TAG
                )
            }
            viewMyContactsArrowBack.setOnClickListener {
                requireActivity().finish()
                requireActivity().overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
            }

        }
    }

    /**
     * Called when a contact is added through the AddContactDialogFragment.
     *
     * @param name The name of the contact.
     * @param profession The profession of the contact.
     */
    override fun onAddContact(name: String, profession: String) {
        mViewModel.addContact(name, profession)
        binding.recyclerViewMyContacts.smoothScrollToPosition(adapter.itemCount)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MyContactsFragment.
         */
        @JvmStatic
        fun newInstance() = MyContactsFragment()
    }
}
