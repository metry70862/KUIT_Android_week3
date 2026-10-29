package com.sadturtleman.kuit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.sadturtleman.kuit.bookmarks.BookmarksScreen
import com.sadturtleman.kuit.bookmarks.BookmarksViewModel
import com.sadturtleman.kuit.detail.DetailScreen
import com.sadturtleman.kuit.detail.DetailViewModel
import com.sadturtleman.kuit.home.HomeScreen
import com.sadturtleman.kuit.home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data class Detail(val id: String) : NavKey

@Serializable
data object Bookmarks : NavKey

@Composable
fun App() {

    val homeViewModel: HomeViewModel = hiltViewModel()

    val bookmarksViewModel: BookmarksViewModel = hiltViewModel()
    val bookmarksState by bookmarksViewModel.state.collectAsStateWithLifecycle()


    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            entry<Home> {
                val state by homeViewModel.state.collectAsStateWithLifecycle()

                HomeScreen(
                    state = state,
                    onIntent = homeViewModel::onIntent,
                    bookmarkCount = bookmarksState.books.size,
                    onBookClick = { id -> //카드 누르면 detail로 이동
                        backStack.add(Detail(id))
                    },
                    onBookmarkClick = { //북마크 누르면 bookmarks로 이동
                        backStack.add(Bookmarks)
                    }
                )
            }

            entry<Detail> { detail ->

                val detailViewModel: DetailViewModel =
                    hiltViewModel(
                        key = "detail-${detail.id}",
                    )

                val state by detailViewModel.state.collectAsStateWithLifecycle()

                DetailScreen(
                    bookId = detail.id,
                    state = state,
                    onIntent = detailViewModel::onIntent,
                    onBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<Bookmarks> {


                BookmarksScreen(
                    state = bookmarksState,
                    onIntent = bookmarksViewModel::onIntent,
                    onBookClick = { id -> //북마크화면에서 책 누르면 detail로 이동
                        backStack.add(Detail(id))
                    },
                    onBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}
