package com.example.minisocialnetwork.presentation.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.minisocialnetwork.presentation.screens.my_contacts.MyContactsFragment
import com.example.minisocialnetwork.presentation.fragments.MyProfileFragment

class ViewPagerAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyProfileFragment.newInstance()
            else -> MyContactsFragment.newInstance()
        }
    }
}