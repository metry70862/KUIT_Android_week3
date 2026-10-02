package com.sadturtleman.kuit.domain.usecase

import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository

class GetBookUseCase(
    private val repository: BookRepository
) {
    operator fun invoke(id: Int): Book? {
        return repository.getBook(id)
    }
}