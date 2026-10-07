package com.sadturtleman.kuit.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.ui.theme.Typography

@Composable
fun BookRow(
    book: Book,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF3EDF7),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = book.title,
                color = Color(0xFF1D1B20),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = book.author,
                color = Color(0xFF79747E),
                style = Typography.bodyMedium,
                fontSize = 14.sp
            )
        }

        Text(
            text = if (book.isBookmarked) "★" else "☆",
            color = if (book.isBookmarked) {
                Color(0xFF6750A4)
            } else {
                Color(0xFF79747E)
            },
            fontSize = 24.sp,
            modifier = Modifier
                .clickable(onClick = onBookmarkClick)
        )
    }
}

@Preview
@Composable
private fun BookRowPrev() {
    BookRow(
        book = Book(
            id = "1",
            title = "Kotlin in Action",
            author = "Dmitry Jemerov",
            pageCount = 360,
            publishedDate = "2017",
            description = "코틀린을 설명하는 책입니다.",
            isBookmarked = true
        ),
        onClick = {}
    ){}
}