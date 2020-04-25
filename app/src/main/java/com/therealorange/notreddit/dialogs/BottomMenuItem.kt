package com.therealorange.notreddit.dialogs

data class BottomMenuItem(
    val selected: Boolean,
    val resId: Int,
    val name: String,
    val action: () -> Unit
)