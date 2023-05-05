package com.example.minisocialnetwork.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minisocialnetwork.databinding.ActivityMyProfileBinding
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
        val result = intent.getStringExtra("email")
        binding.myProfileFullPersonName.text = ParsingData().getUserName(result!!)

    }
}