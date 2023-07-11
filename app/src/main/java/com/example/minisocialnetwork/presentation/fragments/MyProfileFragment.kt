package com.example.minisocialnetwork.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.FragmentMyProfileBinding
import com.example.minisocialnetwork.presentation.fragments.base.BaseFragment
import com.example.minisocialnetwork.presentation.viewmodels.AuthViewModel
import com.example.minisocialnetwork.presentation.viewmodels.MyProfileViewModel
import com.example.minisocialnetwork.util.ParsingData
import dagger.hilt.android.AndroidEntryPoint


/**

 * A fragment representing the user's profile.
 * This fragment extends the BaseFragment class and displays the user's profile information.
 * It retrieves the user's data from the AuthViewModel and sets the user's name in the UI.
 * The fragment also provides a button to navigate to the MyContactsActivity
 * for viewing the user's contacts.
 * @see BaseFragment
 * @see AuthViewModel
 */
@AndroidEntryPoint
class MyProfileFragment :
    BaseFragment<FragmentMyProfileBinding>(FragmentMyProfileBinding::inflate) {

    private val mViewModel: MyProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.result.observe(viewLifecycleOwner) { data ->
            val userName = ParsingData.getUserName(data.email)
            binding.tvMyProfileUserName.text = userName
        }
        setListeners()

    }

    /**

     * Sets up listeners for user interaction in the current fragment.
     */
    private fun setListeners() {
        binding.btMyProfileMyContacts.setOnClickListener {
            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.pager)
            viewPager.currentItem = 1
        }
    }

    companion object {
        /**
         * Creates a new instance of the MyProfileFragment.
         *
         * @return A new instance of MyProfileFragment.
         */
        @JvmStatic
        fun newInstance(): MyProfileFragment = MyProfileFragment()
    }
}