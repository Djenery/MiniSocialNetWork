package com.example.minisocialnetwork.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.minisocialnetwork.databinding.FragmentMyProfileBinding
import com.example.minisocialnetwork.presentation.fragments.base.BaseFragment
import com.example.minisocialnetwork.presentation.viewmodels.AuthViewModel
import com.example.minisocialnetwork.util.ParsingData
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [MyProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MyProfileFragment :
    BaseFragment<FragmentMyProfileBinding>(FragmentMyProfileBinding::inflate) {

    private val mViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.result.observe(viewLifecycleOwner) { data ->
            binding.tvMyProfileUserName.text = ParsingData.getUserName(data.email)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): MyContactsFragment = MyContactsFragment()
    }
}