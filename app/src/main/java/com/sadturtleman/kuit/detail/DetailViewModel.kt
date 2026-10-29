package com.sadturtleman.kuit.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.usecase.GetBookUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getBookUseCase: GetBookUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()


    fun onIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.Load -> {
                load(intent.id)
            }

            DetailIntent.ToggleBookmark -> {
                toggleBookmark()
            }
        }
    }

    //로딩화면 -> 가져오기 -> 가져온 데이터 state에 저장
    private fun load(id: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        runCatching {
            getBookUseCase(id)
        }.onSuccess { book ->
            _state.update {
                it.copy(
                    isLoading = false,
                    book = book
                )
            }
        }.onFailure { error ->
            _state.update {
                it.copy(
                    isLoading = false,
                    error = error.message
                )
            }
        }
    }

    private fun toggleBookmark() {
        val currentBook = _state.value.book ?: return

        val updatedBook = toggleBookmarkUseCase(currentBook.id) ?: return

        _state.update {
            it.copy(
                book = updatedBook
            )
        }
    }
}
