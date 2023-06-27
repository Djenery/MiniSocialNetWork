package com.example.minisocialnetwork.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.FragmentMyProfileBinding
import com.example.minisocialnetwork.presentation.activities.MyContactsActivity
import com.example.minisocialnetwork.presentation.fragments.base.BaseFragment
import com.example.minisocialnetwork.presentation.viewmodels.AuthViewModel
import com.example.minisocialnetwork.util.Flag.NAV_GRAPH
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

    private val mViewModel: AuthViewModel by activityViewModels()

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
            if (NAV_GRAPH) {
                val action =
                    MyProfileFragmentDirections.actionMyProfileFragmentToMyContactsActivity()
                findNavController().navigate(action)
            } else {
                val intent = Intent(context, MyContactsActivity::class.java)
                startActivity(intent)
                requireActivity().overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
            }

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