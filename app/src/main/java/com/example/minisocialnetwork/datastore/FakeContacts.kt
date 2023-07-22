package com.example.minisocialnetwork.datastore

import com.example.minisocialnetwork.domain.model.Contact
import com.example.minisocialnetwork.util.AutoIdIncrement
import com.github.javafaker.Faker
import kotlin.random.Random

/**
 * Creates fake contacts for MyContactsActivity
 */
object FakeContacts {

    /**
     * Gets list of faked contacts
     */
    fun getContacts(): List<Contact> {
        val faker = Faker.instance()
        return (1..10).map {
            Contact(
                id = AutoIdIncrement.getId(),
                photo = getRandomPhoto(),
                name = faker.name().name(),
                profession = faker.job().title()
            )
        }
    }

    /**
     * Gets random photo from chosen list.
     */
    fun getRandomPhoto() = IMAGES[Random.nextInt(9)]

    private val IMAGES = mutableListOf(
        "https://images.unsplash.com/photo-1567532939604-b6b5b0db2604?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=512&h=512&q=80",
        "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=512&h=512&q=80",
        "https://images.unsplash.com/photo-1554151228-14d9def656e4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=512&h=512&q=80",
        "https://images.unsplash.com/photo-1568602471122-7832951cc4c5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=512&h=512&q=80",
        "https://images.unsplash.com/photo-1502823403499-6ccfcf4fb453?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=512&h=512&q=80",
        "https://images.unsplash.com/photo-1528892952291-009c663ce843?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=512&h=512&q=80",
        "https://images.unsplash.com/photo-1519895609939-d2a6491c1196?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=512&h=512&q=80",
        "https://images.unsplash.com/photo-1493106819501-66d381c466f1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=512&h=512&q=80",
        "https://images.unsplash.com/photo-1523824921871-d6f1a15151f1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=512&h=512&q=80",
        "https://images.unsplash.com/photo-1590086782792-42dd2350140d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=512&h=512&q=80"

    )
}
