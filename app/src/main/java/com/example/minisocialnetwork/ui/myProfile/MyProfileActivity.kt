package com.example.minisocialnetwork.ui.myProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.ActivityMyProfileBinding
import com.example.minisocialnetwork.ui.contactsList.MyContactsActivity
import com.example.minisocialnetwork.util.Constants.EMAIL
import com.example.minisocialnetwork.util.ParsingData

class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUserName()
        setListeners()


    }

    private fun setListeners() {
        binding.btMyProfileViewMyContacts.setOnClickListener {
            val intent = Intent(this, MyContactsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }


    private fun setUserName() {
        val result = intent.getStringExtra(EMAIL).toString()
        binding.tvMyProfileUserName.text = ParsingData.getUserName(result)

    }
}