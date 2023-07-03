package com.example.minisocialnetwork.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.presentation.pager.ViewPagerFragment
import com.example.minisocialnetwork.util.Flag.NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main activity for managing contacts.
 */
@AndroidEntryPoint
class MyContactsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_contacts)
        if (NAV_GRAPH) {
            val navHostFragment = NavHostFragment.create(R.navigation.nav_graph_contacts)
            supportFragmentManager.commit {
                add(R.id.container, navHostFragment)
                setPrimaryNavigationFragment(navHostFragment) // equivalent to app:defaultNavHost="true"
            }
        } else {
            val fragment = ViewPagerFragment.newInstance()
            supportFragmentManager.commit {
                add(R.id.container, fragment)
                setReorderingAllowed(true)
            }
        }

    }

}
