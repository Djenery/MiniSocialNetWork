package com.example.minisocialnetwork.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.minisocialnetwork.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity class for user authentication.
 */
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: FragmentAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
            }
        }


    }


}
