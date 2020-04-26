package com.therealorange.notreddit.dialogs

import android.graphics.Bitmap

data class BottomMenuItem(
    val id: Int,
    val selected: Boolean,
    val img: String,
    val name: String,
    val action: (b: BottomMenuItem) -> Unit
)