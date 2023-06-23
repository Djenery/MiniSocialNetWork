package com.example.minisocialnetwork.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.FragmentAuthBinding
import com.example.minisocialnetwork.presentation.fragments.base.BaseFragment
import com.example.minisocialnetwork.presentation.viewmodels.AuthViewModel
import com.example.minisocialnetwork.util.Flag.NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val mViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.isAutoLoginEnabled.observe(this.viewLifecycleOwner) {
            it?.let {
                if (NAV_GRAPH) {
                    navigateByNavGraph(it)
                } else {
                    navigateByFragmentManager(it)
                }
            }
        }
    }

    private fun navigateByNavGraph(it: Boolean) {
        val action: NavDirections = if (it) {
            LoadingFragmentDirections.actionLoadingFragmentToMyProfileFragment()
        } else {
            LoadingFragmentDirections.actionLoadingFragmentToSingUpFragment()
        }
        findNavController().navigate(action)
    }

    private fun navigateByFragmentManager(it: Boolean) {
        parentFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            if (it) {
                replace(R.id.authFragmentContainer, MyContactsFragment.newInstance())
            } else {
                replace(R.id.authFragmentContainer, SingUpFragment.newInstance())
            }
            addToBackStack(null)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): LoadingFragment = LoadingFragment()
    }

}