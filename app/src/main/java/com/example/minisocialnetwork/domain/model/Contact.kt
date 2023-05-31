package com.example.minisocialnetwork.domain.model

/**
 * Data class representing a contact.
 * @property id The unique identifier of the contact.
 * @property photo The photo URL of the contact.
 * @property name The name of the contact.
 * @property profession The profession of the contact.
 */
data class Contact(
    val id: Int,
    val photo: String,
    val name: String,
    val profession: String
)

