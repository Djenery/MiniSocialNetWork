package com.example.minisocialnetwork

import com.example.minisocialnetwork.util.FieldsValidations
import org.junit.Assert
import org.junit.Test

class FieldsValidationsTest {

    @Test
    fun isStringLowerAndUpperCaseTest(){
        val fieldsValidations = FieldsValidations
        val result = fieldsValidations.isMixedCase("hello")
        Assert.assertEquals(result,false)
    }
}