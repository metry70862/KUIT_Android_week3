package com.sadturtleman.kuit.bookmarks

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.ui.component.BookRow
import com.sadturtleman.kuit.ui.component.TopBar
import com.sadturtleman.kuit.ui.theme.KuitTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    state: BookmarksUiState,
    onIntent: (BookmarksIntent) -> Unit,
    onBookClick: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = "북마크",
                onBack = onBack
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                items = state.books,
                key = { book -> book.id }
            ) { book ->
                BookRow(
                    book = book,
                    onClick = {
                        onBookClick(book.id)
                    },
                    onBookmarkClick = {
                        onIntent(
                            BookmarksIntent.ToggleBookmark(book.id)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun BookmarksScreenPreview() {
    KuitTheme {
        BookmarksScreen(
            state = BookmarksUiState(
                books = listOf(
                    Book(
                        id = "1",
                        title = "Kotlin in Action",
                        author = "Dmitry Jemerov",
                        pageCount = 360,
                        publishedDate = "2017",
                        description = "코틀린을 설명하는 책입니다.",
                        isBookmarked = true
                    ),
                    Book(
                        id = "3",
                        title = "Jetpack Compose 완벽 가이드",
                        author = "KUIT",
                        pageCount = 320,
                        publishedDate = "2025",
                        description = "Jetpack Compose를 설명하는 책입니다.",
                        isBookmarked = true
                    )
                )
            ),
            onIntent = {},
            onBookClick = {},
            onBack = {}
        )
    }
}