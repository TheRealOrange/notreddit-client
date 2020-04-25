package com.therealorange.notreddit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.therealorange.notreddit.R
import kotlinx.android.synthetic.main.bottom_sheet_select_post_type.*


public class BottomSheetSelectPostTypeFragment(txt: (()->Unit), img: (()->Unit), imgtxt: (()->Unit), link: (()->Unit)) : BottomSheetDialogFragment() {
    val textPost = txt
    val imgPost = img
    val imgTextPost = imgtxt
    val linkPost = link

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_select_post_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textPostOption.setOnClickListener { textPost.invoke() }
        imagePostOption.setOnClickListener { imgPost.invoke() }
        imageTextPostOption.setOnClickListener { imgTextPost.invoke() }
        linkPostOption.setOnClickListener { linkPost.invoke() }
    }

    companion object {
        fun newInstance(txt: (()->Unit), img: (()->Unit), imgtxt: (()->Unit), link: (()->Unit)): BottomSheetSelectPostTypeFragment? {
            return BottomSheetSelectPostTypeFragment(txt, img, imgtxt, link)
        }
    }
}