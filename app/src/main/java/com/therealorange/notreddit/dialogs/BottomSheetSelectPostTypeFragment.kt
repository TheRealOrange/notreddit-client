package com.therealorange.notreddit.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.therealorange.notreddit.R
import kotlinx.android.synthetic.main.bottom_sheet_select_post_type.*


public class BottomSheetSelectPostTypeFragment(txt: (()->Unit), img: (()->Unit), imgtxt: (()->Unit)) : BottomSheetDialogFragment() {
    val textPost = txt
    val imgPost = img
    val imgTextPost = imgtxt

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_select_post_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textPostOption.setOnClickListener {
            textPost.invoke()
            dismiss()
        }
        imagePostOption.setOnClickListener {
            imgPost.invoke()
            dismiss()
        }
        imageTextPostOption.setOnClickListener {
            imgTextPost.invoke()
            dismiss()
        }
    }

    companion object {
        fun newInstance(txt: (()->Unit), img: (()->Unit), imgtxt: (()->Unit)): BottomSheetSelectPostTypeFragment? {
            return BottomSheetSelectPostTypeFragment(
                txt,
                img,
                imgtxt
            )
        }
    }
}