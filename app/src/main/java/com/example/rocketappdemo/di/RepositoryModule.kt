package com.example.rocketappdemo.di

import com.example.rocketappdemo.data.repository.RocketsRepository
import com.example.rocketappdemo.data.repository.RocketsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    internal abstract fun bindRocketsRepository(repository: RocketsRepositoryImpl) :RocketsRepository
}