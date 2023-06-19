package com.example.minisocialnetwork.presentation.myProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minisocialnetwork.R
import com.example.minisocialnetwork.databinding.ActivityMyProfileBinding
import com.example.minisocialnetwork.presentation.contactsList.MyContactsActivity
import com.example.minisocialnetwork.util.Constants.EMAIL
import com.example.minisocialnetwork.util.ParsingData

/**
 * Activity class for displaying the user's profile.
 */
class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUserName()
        setListeners()
    }


    /**
     * Set click listeners for UI elements.
     */

    private fun setListeners() {
        binding.btMyProfileViewMyContacts.setOnClickListener {
            val intent = Intent(this, MyContactsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    /**
     * Set the user name in the UI.
     */
    private fun setUserName() {
        val result = intent.getStringExtra(EMAIL).toString()
        binding.tvMyProfileUserName.text = ParsingData.getUserName(result)

    }
}