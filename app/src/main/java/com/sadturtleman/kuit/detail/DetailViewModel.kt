package com.sadturtleman.kuit.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.usecase.GetBookUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import com.sadturtleman.kuit.presentation.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

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


    private fun load(id: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        try {
            val book =
                getBookUseCase(id)

            _state.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    book = book,
                    error = null,
                )
            }
        } catch (error: CancellationException) {
            throw error
        } catch (error: HttpException) {
            val message =
                if (error.code() == 404) {
                    "책을 찾을 수 없습니다"
                } else {
                    error.toUserMessage()
                }

            showError(message)
        } catch (error: Exception) {
            val message =
                error.toUserMessage()

            showError(message)
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

    private fun showError(message: String) {
        _state.update {
            it.copy(
                isLoading = false,
                book = null,
                error = message
            )
        }
    }
}
