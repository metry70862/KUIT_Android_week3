package com.sadturtleman.kuit.domain.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val pageCount: Int? = null,
    val publishedDate: String? = null,
    val description: String = "",
    val isBookmarked: Boolean = false
)