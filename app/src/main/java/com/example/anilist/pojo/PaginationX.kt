package com.example.anilist.pojo

data class PaginationX(
    val current_page: Int,
    val has_next_page: Boolean,
    val items: Items,
    val last_visible_page: Int
)