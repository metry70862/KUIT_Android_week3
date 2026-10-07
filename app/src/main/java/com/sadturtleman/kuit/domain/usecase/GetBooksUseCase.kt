package com.sadturtleman.kuit.domain.usecase

import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository
class GetBooksUseCase(      //viewmodel이 직접 리포를 건드리지 않고 useCase사용
    private val repository: BookRepository
) {
    suspend operator fun invoke(query: String): List<Book> {
        return repository.getBooks(query)
    }
}
//여기에 나중에 원하는 비즈니스 로직 추가 가능