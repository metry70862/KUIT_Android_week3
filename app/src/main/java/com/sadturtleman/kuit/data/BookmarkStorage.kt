package com.sadturtleman.kuit.data

import com.sadturtleman.kuit.domain.model.Book

object BookmarkStorage {

    private var bookmarkedBooks: List<Book> = emptyList()

    fun getBooks(): List<Book> {
        return bookmarkedBooks
    }

    fun isBookmarked(id: String): Boolean {
        return bookmarkedBooks.any { book ->
            book.id == id
        }
    }

    fun toggle(book: Book): Book {
        val alreadyBookmarked = isBookmarked(book.id)

        val updatedBook = book.copy(
            isBookmarked = !alreadyBookmarked
        )

        bookmarkedBooks = if (alreadyBookmarked) {
            bookmarkedBooks.filterNot { savedBook ->
                savedBook.id == book.id
            }
        } else {
            bookmarkedBooks + updatedBook
        }

        return updatedBook
    }
}