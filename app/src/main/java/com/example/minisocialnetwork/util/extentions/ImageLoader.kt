package com.example.minisocialnetwork.util.extentions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.minisocialnetwork.R
import com.squareup.picasso.Picasso


fun ImageView.urlLoader(url: String) {
    val loader = listOf(1)
    if (loader[0] == 1) {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(this)
    } else {
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(this)

    }
}

