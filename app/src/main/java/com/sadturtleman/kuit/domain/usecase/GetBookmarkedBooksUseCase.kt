package com.sadturtleman.kuit.domain.usecase

import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkedBooksUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(): Flow<List<Book>> {
        return repository.observeBookmarkedBooks()
    }
}
