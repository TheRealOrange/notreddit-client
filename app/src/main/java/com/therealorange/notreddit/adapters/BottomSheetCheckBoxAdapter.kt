package com.therealorange.notreddit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.therealorange.notreddit.R
import com.therealorange.notreddit.dialogs.BottomCheckBoxItem
import kotlinx.android.synthetic.main.bottom_sheet_menu_item.view.*

class BottomSheetCheckBoxAdapter(private var items: List<BottomCheckBoxItem>) : RecyclerView.Adapter<BottomSheetCheckBoxAdapter.BottomSheetMenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetMenuViewHolder {
        return BottomSheetMenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_checkbox_item, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BottomSheetMenuViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItem(b: BottomCheckBoxItem, position: Int) {
        val temp = items.toMutableList()
        temp[position] = b
        items = temp.toList()
        notifyItemChanged(position)
    }

    fun getItem(position: Int) = items[position]

    class BottomSheetMenuViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: BottomCheckBoxItem) {
            with(view) {
                bottom_menu_title.text = item.name
                bottom_menu_icon.isSelected = false

                setOnClickListener { item.action() }
            }
        }
    }

}