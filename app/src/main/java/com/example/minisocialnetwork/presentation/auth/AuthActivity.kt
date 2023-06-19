package com.example.minisocialnetwork.presentation.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.presentation.LoadingFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity class for user authentication.
 */
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
        val fragment = LoadingFragment.newInstance()
        supportFragmentManager.commit {
            add(R.id.authFragmentContainer, fragment)
            setReorderingAllowed(true)
        }
    }
}


