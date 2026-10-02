package com.sadturtleman.kuit.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.ui.component.BookRow
import com.sadturtleman.kuit.ui.theme.KuitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
    onBookClick: (Int) -> Unit,
    onBookmarkClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KUIT Books") },
                actions = {
                    TextButton(
                        onClick = { onIntent(HomeIntent.Refresh) }
                    ) {
                        Text(
                            "새로고침"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF3EDF7),
                )
            )
        },
        floatingActionButton = {
            Text(
                text = "★ 북마크 2",
                color = Color(0xFF6750A4),
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                modifier = Modifier
                    .background(
                        color = Color(0xFFEADDFF),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable(onClick = onBookmarkClick)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                state.error != null -> Text("에러")
                else -> {
                    LazyColumn(
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
                                    onIntent(HomeIntent.ToggleBookmark(book.id))
                                }
                            )
                            Spacer(modifier = Modifier.padding(6.dp))
                        }
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun HomeScreenPreview() {
    KuitTheme {
        HomeScreen(
            state = HomeState(
                isLoading = false,
                books = listOf(
                    Book(
                        id = 1,
                        title = "Kotlin in Action",
                        author = "Dmitry Jemerov",
                        pages = 360,
                        year = 2017,
                        description = "코틀린을 설명하는 책입니다.",
                        isBookmarked = true
                    ),
                    Book(
                        id = 2,
                        title = "Clean Architecture",
                        author = "Robert C. Martin",
                        pages = 432,
                        year = 2017,
                        description = "소프트웨어 설계 원칙을 설명합니다.",
                        isBookmarked = false
                    )
                ),
                error = null
            ),
            onIntent = {},
            onBookClick = {},
            onBookmarkClick = {}
        )
    }
}


