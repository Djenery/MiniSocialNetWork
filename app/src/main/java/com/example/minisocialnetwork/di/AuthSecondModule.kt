package com.example.minisocialnetwork.di

import com.example.minisocialnetwork.data.AuthRepositoryImpl
import com.example.minisocialnetwork.data.repository.StoreUserData
import com.example.minisocialnetwork.domain.repository.LocalAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthSecondModule {


    @Binds
    @Singleton
    abstract fun bindAuthRepo (
        authRepositoryImpl: AuthRepositoryImpl
    ): LocalAuthRepository

}