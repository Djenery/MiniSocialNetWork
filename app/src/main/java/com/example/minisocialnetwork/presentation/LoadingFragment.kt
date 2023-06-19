package com.example.minisocialnetwork.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.data.SingUpModel
import com.example.minisocialnetwork.databinding.FragmentAuthBinding
import com.example.minisocialnetwork.presentation.auth.SingUpViewModel
import com.example.minisocialnetwork.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val mViewModel: SingUpViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel.result.observe(this.viewLifecycleOwner) { data ->
            autoLogin(data)
        }

        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    private fun autoLogin(data: SingUpModel) {
        if (data.email.isNotEmpty() && data.password.isNotEmpty()) {

        }

    }


    companion object {
        @JvmStatic
        fun newInstance(): LoadingFragment {
            return LoadingFragment()
        }
    }

}