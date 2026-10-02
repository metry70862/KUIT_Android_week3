package com.sadturtleman.kuit.data.model

import com.sadturtleman.kuit.domain.model.Book

data class BookDto(
    val book_id: Int,
    val book_title: String,
    val book_author: String,
    val page_count: Int,
    val published_year: Int,
    val book_description: String,
    val is_bookmarked: Boolean
)

fun BookDto.toBook(): Book {
    return Book(
        id = book_id,
        title = book_title,
        author = book_author,
        pages = page_count,
        year = published_year,
        description = book_description,
        isBookmarked = is_bookmarked
    )
}