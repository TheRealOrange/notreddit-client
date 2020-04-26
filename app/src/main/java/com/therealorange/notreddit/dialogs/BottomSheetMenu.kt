package com.therealorange.notreddit.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.therealorange.notreddit.R
import com.therealorange.notreddit.adapters.BottomSheetMenuAdapter
import com.therealorange.notreddit.dialogs.BottomMenuItem
import kotlinx.android.synthetic.main.bottom_sheet_menu.view.*

class BottomSheetMenu(private val context: Context,
                      private val items: List<BottomMenuItem>) {

    private val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context)
    lateinit var adapter: BottomSheetMenuAdapter

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_menu, null)
        bottomSheetDialog.setContentView(view)

        with(view) {
            adapter = BottomSheetMenuAdapter(items)
            bottom_sheet_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            bottom_sheet_recycler.adapter = adapter
        }
    }

    fun addItem(item: BottomMenuItem) = adapter.addItem(item)
    fun updateItem(item: BottomMenuItem, position: Int) = adapter.updateItem(item, position)

    fun show() {
        bottomSheetDialog.show()
    }
}