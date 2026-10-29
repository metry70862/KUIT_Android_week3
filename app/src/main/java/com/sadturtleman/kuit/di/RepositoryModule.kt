package com.sadturtleman.kuit.di

import com.sadturtleman.kuit.data.repository.BookRepositoryImpl
import com.sadturtleman.kuit.domain.repository.BookRepository
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
    abstract fun bindBookRepository(
        impl: BookRepositoryImpl,
    ): BookRepository
}