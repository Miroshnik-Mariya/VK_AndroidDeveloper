package io.mmaltsev.vkeducation.domain.model

data class App(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val iconRes: Int
)