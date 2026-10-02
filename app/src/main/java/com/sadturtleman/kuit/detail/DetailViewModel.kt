package com.sadturtleman.kuit.detail

import androidx.lifecycle.ViewModel
import com.sadturtleman.kuit.domain.usecase.GetBookUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetailViewModel(
    private val bookId: Int,
    private val getBookUseCase: GetBookUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onIntent(intent: DetailIntent) {
        when (intent) {
            DetailIntent.ToggleBookmark -> {
                toggleBookmark()
            }
        }
    }

    //로딩화면 -> 가져오기 -> 가져온 데이터 state에 저장
    private fun load() {
        _state.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        val book = getBookUseCase(bookId)

        _state.update {
            it.copy(
                isLoading = false,
                book = book
            )
        }
    }



    private fun toggleBookmark() { //해당책의 id를 참고해서 북마크 상태 변경
        val updatedBook = toggleBookmarkUseCase(bookId)
        _state.update {
            it.copy(
                book = updatedBook,
                error = null
            )
        }
    }
}