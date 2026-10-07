package com.sadturtleman.kuit.domain.repository

import com.sadturtleman.kuit.domain.model.Book

interface BookRepository {

    suspend fun getBooks(query: String): List<Book>
    suspend fun getBook(id: String): Book?
    fun toggleBookmark(id: String): Book?
    fun getBookmarkedBooks(): List<Book>

}