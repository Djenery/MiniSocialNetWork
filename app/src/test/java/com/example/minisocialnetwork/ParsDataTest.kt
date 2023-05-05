package com.example.minisocialnetwork

import com.example.minisocialnetwork.util.ParsingData
import org.junit.Assert.assertEquals
import org.junit.Test

class ParsDataTest {

    @Test
    fun getUserNameTest1(){
        val parsData = ParsingData()
        val result = parsData.getUserName("Djenery1994@gmail.com")
        assertEquals("Djenery",result)
    }

    @Test
    fun getUserNameTest2(){
        val parsData = ParsingData()
        val result = parsData.getUserName("Vladyslav.Prokopenko@gmail.com")
        assertEquals("Vladyslav Prokopenko",result)
    }
}