package com.sadturtleman.kuit.domain.usecase

import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(id: String): Book? {
        return repository.toggleBookmark(id)
    }
}
