package com.sadturtleman.kuit.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.usecase.GetBooksUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException

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

            is HomeIntent.Search -> {
                search(intent.query)
            }

        }
    }

    private fun search(query: String) {
        if (query.isBlank()) {
            return
        }
        load(query)
    }

    fun load(query: String=_state.value.query) = viewModelScope.launch { //코루틴 사용
        //책 가져오기전 로딩
        _state.update {
            it.copy(
                isLoading = true,
                error = null,
                query = query

            )
        }
        runCatching {
            getBooksUseCase(query)
        }.onSuccess { books -> //성공시 책 가져온 후 로딩 화면 없애고 책 리스트 갱신
            _state.update {
                it.copy(
                    isLoading = false,
                    books = books
                )
            }
        }.onFailure { error -> //실패시 로딩 화면 없애고 에러 메시지 갱신
            _state.update {
                it.copy(
                    isLoading = false,
                    error = translateErrorMessage(error)
                )
            }
        }


    }

    fun toggleBookmark(id: String) {
        val updatedBook = toggleBookmarkUseCase(id) ?: return //책을 찾지 못하는 경우는 바로 종료

        val currentBooks = _state.value.books

        val updatedBooks = currentBooks.map { book ->
            if (book.id == id) {
                updatedBook
            } else {
                book
            }
        }

        _state.update {
            it.copy(
                books = updatedBooks
            )
        }
    }


    fun translateErrorMessage(error: Throwable): String {
        return when (error) {
            is UnknownHostException -> "인터넷 연결을 확인하세요."
            else -> "책을 불러오지 못했습니다."
        }
    }
}