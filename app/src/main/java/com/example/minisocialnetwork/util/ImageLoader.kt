package com.example.minisocialnetwork.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.minisocialnetwork.R

object ImageLoader {

    fun ImageView.urlLoader(url: String) {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(this)
    }
}