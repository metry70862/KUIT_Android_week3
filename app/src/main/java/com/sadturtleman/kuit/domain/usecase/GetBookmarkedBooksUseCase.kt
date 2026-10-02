package com.sadturtleman.kuit.domain.usecase

import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository

class GetBookmarkedBooksUseCase(
    private val repository: BookRepository
) {
    operator fun invoke(): List<Book> {
        return repository.getBooks().filter { // 전체 책 중에서 북마크된 책만 골라서 반환
            it.isBookmarked
        }
    }
}