package com.sadturtleman.kuit.data.repository

import com.sadturtleman.kuit.data.BookApi
import com.sadturtleman.kuit.data.model.toBook
import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository
import com.sadturtleman.kuit.BuildConfig
import com.sadturtleman.kuit.data.BookmarkStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val api: BookApi
) : BookRepository {

    private var bookList: List<Book> = emptyList()
    private val bookmarkedIds =
        MutableStateFlow(
            BookmarkStorage.getBooks() //북마크 스토리지의 책을 모두 불러와 id만 추출
                .map { book -> book.id }
                .toSet()
        )

    override fun observeBooks(query: String): Flow<List<Book>> {

        val serverFlow = flow {
            val response = api.searchBooks(
                query = query,
                apiKey = BuildConfig.BOOKS_API_KEY
            )
            emit(response.items)
        }

        val resultFlow = combine(
            serverFlow,
            bookmarkedIds
        )
        { volumeDtos, ids ->
            val books = volumeDtos.map { volumeDto ->
                val book = volumeDto.toBook()
                book.copy(isBookmarked = book.id in ids)
            }
            bookList = books
            books
        }

        return resultFlow
    }

    override suspend fun getBook(id: String): Book {
        val volumeDto = api.getBook(
            id = id,
            apiKey = BuildConfig.BOOKS_API_KEY
        )
        val book = volumeDto.toBook()
        val isBookmarked = book.id in bookmarkedIds.value
        val updateBook = book.copy(isBookmarked = isBookmarked)
        return updateBook
    }

    override fun toggleBookmark(id: String): Book? {
        val book = bookList.firstOrNull { book ->
            book.id == id
        } ?: return null

        val updatedBook = BookmarkStorage.toggle(book)

        bookmarkedIds.update { ids ->
            if (updatedBook.isBookmarked) {
                ids + id
            } else {
                ids - id
            }
        }
        return updatedBook
    }

    override fun observeBookmarkedBooks(): Flow<List<Book>> =
        bookmarkedIds.map { BookmarkStorage.getBooks() }
}