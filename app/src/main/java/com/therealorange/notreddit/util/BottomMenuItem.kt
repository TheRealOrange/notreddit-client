package com.therealorange.notreddit.util

data class BottomMenuItem(
    val selected: Boolean,
    val resId: Int,
    val name: String,
    val action: () -> Unit
)