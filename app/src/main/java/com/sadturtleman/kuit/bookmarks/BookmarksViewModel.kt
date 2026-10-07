package com.sadturtleman.kuit.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.usecase.GetBookmarkedBooksUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookmarksViewModel(
    private val getBookmarkedBooksUseCase: GetBookmarkedBooksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookmarksUiState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    //북마크 목록 불러오고 클릭하면 해제
    fun onIntent(intent: BookmarksIntent) {
        when (intent) {
            BookmarksIntent.Refresh -> load()

            is BookmarksIntent.ToggleBookmark -> {
                toggleBookmark(intent.id)
            }
        }
    }

    fun load() = viewModelScope.launch { //북마크된 책만 가져오기
        val books = getBookmarkedBooksUseCase()

        _state.update {
            it.copy(books = books)
        }
    }

    private fun toggleBookmark(id: String) {
        toggleBookmarkUseCase(id)

        val currentBooks = _state.value.books

        val updatedBooks = currentBooks.filterNot { book -> //filterNot은 true인 아이템이 제거됨(map은 교체)
            book.id == id
        }

        _state.update {
            it.copy(
                books = updatedBooks
            )
        }
    }
}