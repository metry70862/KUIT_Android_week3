package com.sadturtleman.kuit.domain.usecase

import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository

class GetBookUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(id: String): Book? {
        return repository.getBook(id)
    }
}