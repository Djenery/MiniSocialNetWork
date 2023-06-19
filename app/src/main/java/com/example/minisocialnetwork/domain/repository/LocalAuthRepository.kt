package com.example.minisocialnetwork.domain.repository

import com.example.minisocialnetwork.data.SingUpModel

interface LocalAuthRepository {

    suspend fun saveSignUpCredentials(singUpModel: SingUpModel)

    suspend fun getSignUpCredentials(): SingUpModel


}