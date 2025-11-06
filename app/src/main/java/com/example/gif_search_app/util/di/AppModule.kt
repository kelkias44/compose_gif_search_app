package com.example.gif_search_app.util.di

import com.example.gif_search_app.util.network.NetworkManager
import com.example.gif_search_app.util.network.NetworkManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    
    @Binds
    @Singleton
    abstract fun bindNetworkManager(
        networkManagerImpl: NetworkManagerImpl
    ): NetworkManager
}
