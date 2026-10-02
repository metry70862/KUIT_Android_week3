package com.sadturtleman.kuit.domain.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val pages: Int,
    val year: Int,
    val description: String,
    val isBookmarked: Boolean
)