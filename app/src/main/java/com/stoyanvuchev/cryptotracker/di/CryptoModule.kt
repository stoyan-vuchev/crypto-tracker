package com.stoyanvuchev.cryptotracker.di

import android.content.Context
import com.stoyanvuchev.cryptotracker.core.data.networking.HttpClientFactory
import com.stoyanvuchev.cryptotracker.crypto.data.local.LocalDatabase
import com.stoyanvuchev.cryptotracker.crypto.data.local.LocalDatabaseFactory
import com.stoyanvuchev.cryptotracker.crypto.data.networking.RemoteDataSourceImpl
import com.stoyanvuchev.cryptotracker.crypto.data.repository.CoinRepositoryImpl
import com.stoyanvuchev.cryptotracker.crypto.domain.networking.RemoteDataSource
import com.stoyanvuchev.cryptotracker.crypto.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory.createInstance(CIO.create())
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(httpClient: HttpClient): RemoteDataSource {
        return RemoteDataSourceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return LocalDatabaseFactory.createPersistentInstance(context)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(
        remoteDataSource: RemoteDataSource,
        localDatabase: LocalDatabase
    ): CoinRepository {
        return CoinRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDatabaseDao = localDatabase.dao
        )
    }

}