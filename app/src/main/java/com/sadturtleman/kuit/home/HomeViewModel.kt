package com.sadturtleman.kuit.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadturtleman.kuit.domain.usecase.GetBooksUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import com.sadturtleman.kuit.presentation.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {
    private val query = MutableStateFlow("Android")
    private val refreshTrigger = MutableStateFlow(0) //리프레시 버튼 눌리면 값 변경
    private val requestQueryFlow = combine(
        query,
        refreshTrigger
    ) { query, _ -> query } //리프레시 트리거는 쓰레기값


    //검색어 받아서 Flow<HomeState>를 만들고 stateFlow로 변환
    private val homeStateFlow = requestQueryFlow.flatMapLatest { query ->
        val booksFlow = getBooksUseCase(query)

        val stateFlow = booksFlow.map { books ->
            HomeState(
                isLoading = false,
                books = books,
                error = null,
                query = query
            )
        }
        val sateStateFlow = stateFlow.catch { error ->
            if (error is CancellationException) {
                throw error
            }

            val errorState = HomeState(
                isLoading = false,
                books = emptyList(),
                error = error.toUserMessage(),
                query = query
            )
            emit(errorState)
        }

        sateStateFlow
    }

    val state = homeStateFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState(
            isLoading = true,
            query = query.value
        )
    )

    private fun refresh() {
        refreshTrigger.update { it + 1 }
    }

    private fun search(newQuery: String) {
        query.value = newQuery
    }

    private fun toggleBookmark(id: String) {
        toggleBookmarkUseCase(id)
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Refresh -> {
                refresh()
            }

            is HomeIntent.Search -> {
                search(intent.query)
            }

            is HomeIntent.ToggleBookmark -> {
                toggleBookmark(intent.id)
            }
        }
    }
}
