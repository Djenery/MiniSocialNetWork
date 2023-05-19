package com.example.minisocialnetwork.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.ActivityMyContactsBinding

class MyContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}