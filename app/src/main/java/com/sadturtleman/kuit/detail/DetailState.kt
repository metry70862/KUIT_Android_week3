package com.sadturtleman.kuit.detail

import com.sadturtleman.kuit.domain.model.Book

data class DetailUiState(
    val isLoading: Boolean = false,
    val book: Book? = null,
    val error: String? = null
)

sealed interface DetailIntent {
    data class Load(
        val id: String
    ) : DetailIntent

    data object ToggleBookmark : DetailIntent
}