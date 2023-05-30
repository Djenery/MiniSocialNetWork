package com.example.minisocialnetwork.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minisocialnetwork.databinding.ActivityMyProfileBinding
import com.example.minisocialnetwork.util.Constants.EMAIL
import com.example.minisocialnetwork.util.ParsingData

class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUserName()
    }

    private fun setUserName() {
        val result = intent.getStringExtra(EMAIL)
        binding.tvMyProfileUserName.text = ParsingData.getUserName(result!!)

    }
}