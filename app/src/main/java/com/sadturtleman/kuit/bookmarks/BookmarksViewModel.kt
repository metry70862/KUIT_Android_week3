package com.sadturtleman.kuit.bookmarks

import androidx.lifecycle.ViewModel
import com.sadturtleman.kuit.domain.usecase.GetBookmarkedBooksUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

    fun load() { //북마크된 책만 가져오기
        val books = getBookmarkedBooksUseCase()

        _state.update {
            it.copy(books = books)
        }
    }

    private fun toggleBookmark(id: Int) {
        toggleBookmarkUseCase(id)
        load() //북마크 변경 후 다시 로드해서 사용자에게 클릭즉시 없어지게 봉;ㅁ
    }
}