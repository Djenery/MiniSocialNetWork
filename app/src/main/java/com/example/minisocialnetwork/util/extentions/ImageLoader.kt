package com.example.minisocialnetwork.util.extentions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.minisocialnetwork.R

/**
 * Loads an image from a URL into an ImageView using Glide library.
 * @param url The URL of the image to load.
 */
fun ImageView.urlLoader(url: String) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .placeholder(R.drawable.ic_person)
        .error(R.drawable.ic_person)
        .into(this)
}

