package com.sadturtleman.kuit.home

import androidx.lifecycle.ViewModel
import com.sadturtleman.kuit.domain.usecase.GetBooksUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val getBooksUseCase: GetBooksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        onIntent(HomeIntent.Refresh)
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Refresh -> load()

            is HomeIntent.ToggleBookmark -> {
                toggleBookmark(intent.id)
            }
        }
    }

    fun load() {

        //책 가져오기전 로딩
        _state.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        val books = getBooksUseCase()

        //책 가져온 후 로딩 화면 없애고 책 리스트 갱신
        _state.update {
            it.copy(
                isLoading = false,
                books = books
            )
        }


    }

    fun toggleBookmark(id: Int) {
        toggleBookmarkUseCase(id)

        val books = getBooksUseCase()

        //북마크 변경후 다시 업데이트
        _state.update {
            it.copy(
                books = books
            )
        }
    }
}