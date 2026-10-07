package com.sadturtleman.kuit.domain.usecase

import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository

class GetBookmarkedBooksUseCase(
    private val repository: BookRepository
) {
    operator fun invoke(): List<Book> {
        return repository.getBookmarkedBooks()
    }
}