package com.sadturtleman.kuit.domain.repository

import com.sadturtleman.kuit.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun observeBooks(query: String): Flow<List<Book>>

    suspend fun getBook(id: String): Book?

    fun toggleBookmark(id: String): Book?

    fun observeBookmarkedBooks(): Flow<List<Book>>
}