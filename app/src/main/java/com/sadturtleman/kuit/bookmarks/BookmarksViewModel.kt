package com.sadturtleman.kuit.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.usecase.GetBookmarkedBooksUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getBookmarkedBooksUseCase: GetBookmarkedBooksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val bookmarkedBooksFlow = getBookmarkedBooksUseCase()

    private val bookmarksStateFlow =
        bookmarkedBooksFlow.map { books ->
            BookmarksUiState(
                books = books
            )
        }

    val state = bookmarksStateFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BookmarksUiState()
    )

    fun onIntent(intent: BookmarksIntent) {
        when (intent) {
            is BookmarksIntent.ToggleBookmark -> {
                toggleBookmark(intent.id)
            }
        }
    }

    private fun toggleBookmark(id: String) {
        toggleBookmarkUseCase(id)
    }
}