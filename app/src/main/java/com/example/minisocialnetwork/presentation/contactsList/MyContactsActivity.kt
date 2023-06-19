package com.example.minisocialnetwork.presentation.contactsList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.util.Flag.NAV_GRAPH

/**
 * The main activity for managing contacts.
 */
class MyContactsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_contacts)
        if (NAV_GRAPH) {
            val navHostFragment = NavHostFragment.create(R.navigation.nav_graph)
            supportFragmentManager.commit {
                add(R.id.fragment_container, navHostFragment)
                setPrimaryNavigationFragment(navHostFragment) // equivalent to app:defaultNavHost="true"
            }
        } else {
            val fragment = MyContactsFragment.newInstance()
            supportFragmentManager.commit {
                add(R.id.fragment_container, fragment)
                addToBackStack(null)
                setReorderingAllowed(true)
            }
        }
    }

}
