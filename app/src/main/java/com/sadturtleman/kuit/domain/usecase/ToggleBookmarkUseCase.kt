package com.sadturtleman.kuit.domain.usecase

import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository

class ToggleBookmarkUseCase(
    private val repository: BookRepository
) {
    operator fun invoke(id: Int): Book? {
        return repository.toggleBookmark(id)
    }
}