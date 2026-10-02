package com.sadturtleman.kuit.data.repository


import com.sadturtleman.kuit.data.model.BookDto
import com.sadturtleman.kuit.data.model.toBook
import com.sadturtleman.kuit.domain.model.Book
import com.sadturtleman.kuit.domain.repository.BookRepository

class FakeBookRepository : BookRepository {

    private var bookList = listOf(
        BookDto(
            book_id = 1,
            book_title = "Kotlin in Action",
            book_author = "Dmitry Suhyeok",
            page_count = 360,
            published_year = 2017,
            book_description = "코틀린의 기본 문법과 실전 활용법을 설명하는 책입니다.",
            is_bookmarked = true
        ),
        BookDto(
            book_id = 2,
            book_title = "Clean Architecture",
            book_author = "Byeon C. Martin",
            page_count = 432,
            published_year = 2017,
            book_description = "소프트웨어 구조와 설계 원칙을 다루는 책입니다.",
            is_bookmarked = false
        ),
        BookDto(
            book_id = 3,
            book_title = "Jetpack Compose 완벽 가이드",
            book_author = "KUIT",
            page_count = 320,
            published_year = 2025,
            book_description = "Jetpack Compose를 활용한 안드로이드 UI 개발을 설명합니다. ",
            is_bookmarked = true
        ),
        BookDto(
            book_id = 4,
            book_title = "Refactoring II",
            book_author = "Martin Fowler",
            page_count = 448,
            published_year = 2019,
            book_description = "기존 코드를 안전하게 개선하는 리팩터링 기법을 소개합니다.",
            is_bookmarked = false
        ),
        BookDto(
            book_id = 5,
            book_title = "Effective Kotlin",
            book_author = "Marcin Moskala",
            page_count = 440,
            published_year = 2020,
            book_description = "더 안전하고 효율적인 코틀린 코드를 작성하는 방법을 설명합니다.",
            is_bookmarked = false
        )
    )

    override fun getBooks(): List<Book> {  //dto를 book으로 변환
        return bookList.map { bookDto ->
            bookDto.toBook()
        }
    }

    override fun getBook(id: Int): Book? { //id에 해당하는 book을 반환
        return bookList
            .firstOrNull { bookDto -> bookDto.book_id == id }
            ?.toBook()
    }

    override fun toggleBookmark(id: Int): Book? {
        bookList = bookList.map { it ->
            if (it.book_id == id) {
                it.copy(is_bookmarked = !it.is_bookmarked)
            } else {
                it
            }
        }
        return getBook(id)
    }
}
