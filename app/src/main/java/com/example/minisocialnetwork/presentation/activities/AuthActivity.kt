package com.example.minisocialnetwork.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.presentation.fragments.LoadingFragment
import com.example.minisocialnetwork.util.Flag.NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity class for user authentication.
 */
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (savedInstanceState == null) {
            if (NAV_GRAPH) {
                val navHostFragment = NavHostFragment.create(R.navigation.nav_graph_authorization)
                supportFragmentManager.commit {
                    add(R.id.authFragmentContainer, navHostFragment)
                    setPrimaryNavigationFragment(navHostFragment)
                }
            } else {
                val fragment = LoadingFragment.newInstance()
                supportFragmentManager.commit {
                    add(R.id.authFragmentContainer, fragment)
                    setReorderingAllowed(true)
                }
            }
        }
    }

}



