package com.sadturtleman.kuit.data.repository

import com.sadturtleman.kuit.data.BookApi
import com.sadturtleman.kuit.data.model.toBook
import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository
import com.sadturtleman.kuit.BuildConfig
import com.sadturtleman.kuit.data.BookmarkStorage

class BookRepositoryImpl(
    private val api: BookApi
) : BookRepository {

    private var bookList: List<Book> = emptyList()

    override suspend fun getBooks(query: String): List<Book> {
        bookList = api.searchBooks(
            query = query,
            apiKey = BuildConfig.BOOKS_API_KEY
        ).items.map { volumeDto ->
            val book = volumeDto.toBook()

            book.copy(
                isBookmarked = BookmarkStorage.isBookmarked(book.id)
            )
        }

        return bookList
    }

    override suspend fun getBook(id: String): Book {
        return api.getBook(
            id = id,
            apiKey = BuildConfig.BOOKS_API_KEY
        ).toBook()
    }

    override fun toggleBookmark(id: String): Book? {
        bookList = bookList.map { book ->
            if (book.id == id) {
                book.copy(
                    isBookmarked = !book.isBookmarked
                )
            } else {
                book
            }
        }

        val updatedBook = bookList.firstOrNull { book ->
            book.id == id
        } ?: return null

        BookmarkStorage.toggle(updatedBook)

        return updatedBook
    }

    override fun getBookmarkedBooks(): List<Book> {
        return BookmarkStorage.getBooks()
    }
}