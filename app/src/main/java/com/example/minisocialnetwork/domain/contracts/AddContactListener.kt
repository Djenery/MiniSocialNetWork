package com.example.minisocialnetwork.domain.contracts

/**
 * Interface for adding a contact.
 */
interface AddContactListener {

    /**
     * Called when a contact is added.
     * @param name The name of the contact.
     * @param profession The profession of the contact.
     */
    fun onAddContact(name: String, profession: String)
}