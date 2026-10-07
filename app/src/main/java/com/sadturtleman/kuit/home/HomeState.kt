package com.sadturtleman.kuit.home

import com.sadturtleman.kuit.domain.model.Book

data class HomeState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String? = null,
    val query: String = "android"
)

sealed interface HomeIntent {
    data object Refresh : HomeIntent
    data class ToggleBookmark(val id: String) : HomeIntent
    data class Search(val query: String) : HomeIntent
}

