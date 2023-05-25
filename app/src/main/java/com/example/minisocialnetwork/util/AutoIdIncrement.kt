package com.example.minisocialnetwork.util


object AutoIdIncrement {
    private var counter: Int = 0

    fun getId(): Int {
        return ++counter
    }
}