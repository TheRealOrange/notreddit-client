package com.therealorange.notreddit.util

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.therealorange.notreddit.R
import com.therealorange.notreddit.adapters.BottomSheetCheckBoxAdapter
import com.therealorange.notreddit.adapters.BottomSheetMenuAdapter
import kotlinx.android.synthetic.main.bottom_sheet_menu.view.*

class BottomSheetCheckBox(private val context: Context,
                          private val items: List<BottomCheckBoxItem>) {

    private val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_menu, null)
        bottomSheetDialog.setContentView(view)

        with(view) {
            bottom_sheet_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            bottom_sheet_recycler.adapter = BottomSheetCheckBoxAdapter(items)
        }
    }

    fun show() {
        bottomSheetDialog.show()
    }
}