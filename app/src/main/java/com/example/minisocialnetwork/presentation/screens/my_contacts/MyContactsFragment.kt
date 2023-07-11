package com.example.minisocialnetwork.presentation.screens.my_contacts

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.FragmentMyContactsBinding
import com.example.minisocialnetwork.domain.model.Contact
import com.example.minisocialnetwork.domain.repository.AddContactListener
import com.example.minisocialnetwork.domain.repository.ItemListener
import com.example.minisocialnetwork.domain.repository.MultiSelectListener
import com.example.minisocialnetwork.presentation.adapter.ContactsAdapter
import com.example.minisocialnetwork.presentation.adapter.IndentItemDecoration
import com.example.minisocialnetwork.presentation.fragments.AddContactDialogFragment
import com.example.minisocialnetwork.presentation.fragments.DetailViewFragment
import com.example.minisocialnetwork.presentation.fragments.base.BaseFragment
import com.example.minisocialnetwork.presentation.pager.ViewPagerFragmentDirections
import com.example.minisocialnetwork.util.Constants
import com.example.minisocialnetwork.util.Flag.NAV_GRAPH
import com.example.minisocialnetwork.util.extentions.onItemTouch
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**

 * A fragment that displays a list of contacts.
 */
@AndroidEntryPoint
class MyContactsFragment :
    BaseFragment<FragmentMyContactsBinding>(FragmentMyContactsBinding::inflate),
    AddContactListener {

    private val mViewModel: MyContactsViewModel by viewModels()

    private val adapter: ContactsAdapter by lazy {
        ContactsAdapter(object : ItemListener {
            override fun onRemoveItem(position: Int) {
                removeItem(position)
            }

            override fun onClickItem(contact: Contact, imageView: ImageView) {
                clickItem(contact, imageView)
            }

        }, object : MultiSelectListener {

            override fun addItemToSelectedState(item: Contact) {
                mViewModel.addSelectedItem(item)
            }

            override fun removeItemFromSelectedState(item: Contact) {
                mViewModel.removeSelectedItem(item)
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
            recyclerViewMyContacts.layoutManager = LinearLayoutManager(context)
            recyclerViewMyContacts.adapter = adapter
            recyclerViewMyContacts.addItemDecoration(IndentItemDecoration())
            postponeEnterTransition()
            recyclerViewMyContacts.doOnPreDraw {
                startPostponedEnterTransition()
            }
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

    private fun removeSelectedItems() {
        val itemsToRemove = mViewModel.deleteAllSelectedItems()
        Snackbar.make(
            binding.recyclerViewMyContacts,
            "$itemsToRemove ${getString(R.string.contacts_was_deleted)}",
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.undo)) {
            mViewModel.insertMultipleItems()
        }.show()
    }


    /**

     * Displays a SnackBar with a message indicating that a contact was deleted.
     * The SnackBar includes an "Undo" action that allows the user to undo the deletion.
     * When the "Undo" action is clicked, the RecyclerView is scrolled to the position
     * where the contact was originally inserted.
     */
    private fun showSnackBar() {
        Snackbar.make(
            binding.recyclerViewMyContacts,
            getString(R.string.contact_was_deleted),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.undo)) {
            binding.recyclerViewMyContacts.smoothScrollToPosition(mViewModel.insertAt())
        }.show()
    }

    /**

     * Handles the click event on a contact item.
     * @param contact The clicked contact.
     * @param imageView The ImageView associated with the contact item.
     */
    private fun clickItem(contact: Contact, imageView: ImageView) {
        if (NAV_GRAPH) {
            navigateToDetailViewByNavComponent(contact, imageView)
        } else {
            navigateToDetailViewByFragmentManager(contact)

        }
    }

    /**

     * Navigates to the detail view of a contact using the Navigation component.
     * @param contact The contact to display in the detail view.
     * @param imageView The ImageView associated with the contact item.
     */
    private fun navigateToDetailViewByNavComponent(contact: Contact, imageView: ImageView) {
        val action =
            ViewPagerFragmentDirections.actionMyContactsFragmentToDetailViewFragment(contact)
        val extras = FragmentNavigatorExtras(
            imageView to contact.photo + contact.id
        )
        findNavController().navigate(action, extras)
    }

    /**

     * Navigates to the detail view of a contact using the FragmentManager.
     * @param contact The contact to display in the detail view.
     */
    private fun navigateToDetailViewByFragmentManager(contact: Contact) {
        requireActivity().supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            replace(
                R.id.container, DetailViewFragment.newInstance(
                    contact.name, contact.photo, contact.profession
                )
            )
            addToBackStack(null)
        }

    }

    /**
     * Sets up observers for the ViewModel's contacts list.
     */
    private fun setObservers() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                mViewModel.contactsList.observe(viewLifecycleOwner) { contacts ->
                    adapter.submitList(contacts.toMutableList())
                }
                mViewModel.multiSelectedState.observe(viewLifecycleOwner) {
                    binding.floating.visibility = if (it) View.VISIBLE else View.GONE
                    adapter.changeMultiselectState(it)
                }
                mViewModel.selectedItems.observe(viewLifecycleOwner) {
                    Log.d("MyContactsFragment", "$it")
                    adapter.changeMultiselectItems(it)
                }
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
                    childFragmentManager, AddContactDialogFragment.TAG
                )
            }
            viewMyContactsArrowBack.setOnClickListener {
                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.pager)
                viewPager.currentItem = Constants.MY_PROFILE_PAGE
            }

            floating.setOnClickListener {
                removeSelectedItems()
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
