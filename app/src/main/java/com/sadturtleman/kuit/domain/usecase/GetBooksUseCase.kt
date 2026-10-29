package com.sadturtleman.kuit.domain.usecase

import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(      //viewmodel이 직접 리포를 건드리지 않고 useCase사용
    private val repository: BookRepository
) {
    operator fun invoke(query: String): Flow<List<Book>> {
        return repository.observeBooks(query)
    }
}
//여기에 나중에 원하는 비즈니스 로직 추가 가능
