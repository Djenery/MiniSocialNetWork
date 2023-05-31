package com.example.minisocialnetwork.util

/**
 * Object responsible for generating auto-incremented IDs.
 */
object AutoIdIncrement {
    private var counter: Int = 0

    /**
     * Retrieves the next auto-incremented ID.
     * @return The next auto-incremented ID.
     */
    fun getId(): Int {
        return ++counter
    }
}