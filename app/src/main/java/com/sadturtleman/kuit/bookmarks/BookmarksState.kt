package com.sadturtleman.kuit.bookmarks

import com.sadturtleman.kuit.domain.model.Book

data class BookmarksUiState(
    val books: List<Book> = emptyList(),
)

sealed interface BookmarksIntent {
    data object Refresh : BookmarksIntent

    data class ToggleBookmark(
        val id: Int
    ) : BookmarksIntent
}