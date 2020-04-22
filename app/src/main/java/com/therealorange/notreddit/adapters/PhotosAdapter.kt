package com.therealorange.notreddit.adapters

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.therealorange.notreddit.R
import com.therealorange.notreddit.tabs.FragmentObject
import kotlinx.android.synthetic.main.img_view.*

class PhotosAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    val fragments = mutableListOf<Fragment>()

    override fun getItemCount() = fragments.size

    fun addPhoto(bm : Bitmap): Int {
        fragments.add(object : Fragment() {
            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
            ): View {
                return inflater.inflate(R.layout.img_view, container, false)
            }

            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                arguments?.takeIf { it.containsKey("ARG_OBJECT") }?.apply {
                    photoView.setImageBitmap(bm)
                }
            }
        })
        return fragments.size-1
    }

    fun removePhoto(pos: Int) = fragments.removeAt(pos)

    override fun createFragment(position: Int): Fragment {
        // Return a NEW tab_layout instance in createFragment(int)
        val fragment = fragments[position]
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt("ARG_OBJECT", position + 1)
        }
        return fragment
    }
}