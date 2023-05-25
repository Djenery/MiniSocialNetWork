package com.example.minisocialnetwork.domain.model

import com.example.minisocialnetwork.util.AutoIdIncrement


data class Contact(
    val id: Int,
    val photo: String,
    val name: String,
    val profession: String
)

