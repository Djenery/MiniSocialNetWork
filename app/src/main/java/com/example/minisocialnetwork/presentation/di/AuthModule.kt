package com.example.minisocialnetwork.presentation.di

import android.content.Context
import com.example.minisocialnetwork.data.StoreUserData
import com.example.minisocialnetwork.domain.repository.LocalAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun something(@ApplicationContext context: Context): LocalAuthRepository{
        return StoreUserData(context)
    }



}