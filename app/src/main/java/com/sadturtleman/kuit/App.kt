package com.sadturtleman.kuit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.sadturtleman.kuit.bookmarks.BookmarksScreen
import com.sadturtleman.kuit.bookmarks.BookmarksViewModel
import com.sadturtleman.kuit.data.repository.FakeBookRepository
import com.sadturtleman.kuit.detail.DetailScreen
import com.sadturtleman.kuit.detail.DetailViewModel
import com.sadturtleman.kuit.domain.usecase.GetBookUseCase
import com.sadturtleman.kuit.domain.usecase.GetBookmarkedBooksUseCase
import com.sadturtleman.kuit.domain.usecase.GetBooksUseCase
import com.sadturtleman.kuit.domain.usecase.ToggleBookmarkUseCase
import com.sadturtleman.kuit.home.HomeScreen
import com.sadturtleman.kuit.home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data class Detail(val id: Int) : NavKey

@Serializable
data object Bookmarks : NavKey

@Composable
fun App() {
    //세개의 화면에서 하나의 리포지토리 공유
    val repository = remember {
        FakeBookRepository()
    }
    //홈, 북마크 화면 뷰모델은 여기서 생성
    //디테일 화면 뷰모델은 id 별로 필요하기 때문에 entryProvider에서 생성
    val homeViewModel: HomeViewModel = viewModel {
        HomeViewModel(
            getBooksUseCase = GetBooksUseCase(repository),
            toggleBookmarkUseCase = ToggleBookmarkUseCase(repository)
        )
    }

    val bookmarksViewModel: BookmarksViewModel = viewModel {
        BookmarksViewModel(
            getBookmarkedBooksUseCase = GetBookmarkedBooksUseCase(repository),
            toggleBookmarkUseCase = ToggleBookmarkUseCase(repository)
        )
    }


    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = { //뒤로가기 할때 북바크 상태 다시 불러옴
            homeViewModel.load()
            bookmarksViewModel.load()
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            entry<Home> {
                val state by homeViewModel.state.collectAsStateWithLifecycle()

                HomeScreen(
                    state = state,
                    onIntent = homeViewModel::onIntent,
                    onBookClick = { id -> //카드 누르면 detail로 이동
                        backStack.add(Detail(id))
                    },
                    onBookmarkClick = { //북마크 버튼 누르면 북마크 상태 로드후 이동
                        bookmarksViewModel.load()
                        backStack.add(Bookmarks)
                    }
                )
            }

            entry<Detail> { detail ->

                val detailViewModel: DetailViewModel = viewModel(
                    key = "detail-${detail.id}" //detail 화면마다 책 번호가 다르기 때문에 id를 key에 포함
                ) {
                    DetailViewModel(
                        bookId = detail.id,
                        getBookUseCase = GetBookUseCase(repository),
                        toggleBookmarkUseCase = ToggleBookmarkUseCase(repository)
                    )
                }

                val state by detailViewModel.state.collectAsStateWithLifecycle()

                DetailScreen(
                    state = state,
                    onIntent = detailViewModel::onIntent,
                    onBack = {
                        homeViewModel.load()
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<Bookmarks> {
                val state by bookmarksViewModel.state.collectAsStateWithLifecycle()

                BookmarksScreen(
                    state = state,
                    onIntent = bookmarksViewModel::onIntent,
                    onBookClick = { id -> //북마크화면에서 책 누르면 detail로 이동
                        backStack.add(Detail(id))
                    },
                    onBack = {
                        homeViewModel.load()
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}