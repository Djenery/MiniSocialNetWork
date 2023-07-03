package com.example.minisocialnetwork.presentation.pager

import android.os.Bundle
import android.view.View
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.FragmentViewPagerBinding
import com.example.minisocialnetwork.presentation.fragments.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment :
    BaseFragment<FragmentViewPagerBinding>(FragmentViewPagerBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab1)
                else -> getString(R.string.tab2)
            }
        }.attach()

    }

    companion object {
        @JvmStatic
        fun newInstance() = ViewPagerFragment()
    }

}

