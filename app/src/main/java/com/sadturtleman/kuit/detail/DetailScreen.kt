package com.sadturtleman.kuit.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.ui.component.TopBar
import com.sadturtleman.kuit.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    bookId: String,
    state: DetailUiState,
    onIntent: (DetailIntent) -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(bookId) { //처음 실행, bookId 변경시에 로드
        onIntent(
            DetailIntent.Load(bookId)
        )
    }
    Scaffold(
        topBar = {
            TopBar(
                title = "책 상세",
                onBack = onBack
            )
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.error != null -> {
                    Text(
                        text = state.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.book != null -> {
                    DetailContent(
                        book = state.book,
                        onBookmarkClick = {
                            onIntent(DetailIntent.ToggleBookmark)
                        })
                }
            }
        }
    }
}

@Composable
private fun DetailContent(
    book: Book, onBookmarkClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = book.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(9.dp))

        Text(
            text = book.author,
            style = Typography.bodyMedium,
            color = Color(0xFF79747E)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "${book.pageCount ?: "페이지 정보 없음"}p · ${book.publishedDate ?: "발행이 정보 없음"}",
            style = Typography.bodyMedium,
            color = Color(0xFF79747E)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = if (book.isBookmarked) {
                "★ 북마크 해제"
            } else {
                "☆ 북마크에 추가"
            },
            color = Color.White,
            modifier = Modifier
                .background(
                    color = Color(0xFF6750A4),
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable(onClick = onBookmarkClick)
                .padding(
                    horizontal = 16.dp,
                    vertical = 10.dp
                )
        )

        Spacer(modifier = Modifier.height(34.dp))

        Text(
            text = "소개",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = book.description.take(200),
            style = Typography.bodyMedium,
            fontSize = 14.sp,
            color = Color(0xFF49454F)
        )
    }
}

@Preview
@Composable
private fun DetailScreenPrev() {
    DetailScreen(
        bookId = "1",
        state = DetailUiState(
            isLoading = false,
            book = Book(
                id = "1",
                title = "Kotlin in Action",
                author = "Dmitry Jemerov",
                pageCount = 360,
                publishedDate = "2017",
                description = "코틀린을 설명하는 책입니다. 이 설명은 200까지 표시됩니다. ",
                isBookmarked = true
            )
        ),
        onIntent = {},
        onBack = {}
    )
}