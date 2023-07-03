package com.example.minisocialnetwork.domain.repository

import com.example.minisocialnetwork.domain.model.Contact

interface MultiSelectListener {

    fun addItemToSelectedState(item: Contact)
    fun removeItemFromSelectedState(item: Contact)

}
