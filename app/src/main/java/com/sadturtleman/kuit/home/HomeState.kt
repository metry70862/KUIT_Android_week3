package com.sadturtleman.kuit.home

import com.sadturtleman.kuit.domain.model.Book

data class HomeState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String? = null
)

sealed interface HomeIntent {
    data object Refresh : HomeIntent
    data class ToggleBookmark(val id: Int) : HomeIntent
}

