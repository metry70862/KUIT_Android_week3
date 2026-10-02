package com.sadturtleman.kuit.domain.repository

import com.sadturtleman.kuit.domain.model.Book

interface BookRepository {
    fun getBooks(): List<Book>
    fun getBook(id: Int): Book?
    fun toggleBookmark(id: Int): Book?
}