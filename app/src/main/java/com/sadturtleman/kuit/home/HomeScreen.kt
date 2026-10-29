package com.sadturtleman.kuit.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sadturtleman.kuit.ui.component.BookRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
    onBookClick: (String) -> Unit,
    onBookmarkClick: () -> Unit,
    bookmarkCount: Int,
) {
    var searchText by remember { mutableStateOf("") }
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
                text = "★ 북마크${bookmarkCount}",
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
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                    },
                    label = {
                        Text("검색어")
                    },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        onIntent(
                            HomeIntent.Search(searchText)
                        )
                    }
                ) {
                    Text("검색")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                when {
                    state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                    state.error != null -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = state.error)

                            Spacer(modifier = Modifier.padding(8.dp))

                            Button(
                                onClick = {
                                    onIntent(HomeIntent.Refresh)
                                }
                            ) {
                                Text("다시 시도")
                            }
                        }
                    }

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
}





