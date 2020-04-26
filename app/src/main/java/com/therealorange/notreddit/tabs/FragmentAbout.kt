package com.therealorange.notreddit.tabs

import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Bundle
import android.view.View
import com.therealorange.notreddit.R
import com.therealorange.notreddit.client.WebSocket
import kotlinx.android.synthetic.main.about_page.*
import java.util.*

class FragmentAbout : FragmentObject(R.layout.about_page,"About", EXTRA_ID) {
    var stop = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(extraId) }?.apply {
            if (this.getString(extraId)?.toIntOrNull() != null) {
                WebSocket.sendGetSubNotReddit(this.getString(extraId)!!.toIntOrNull()!!) {
                    subnotredditpageAbout.text = it.about
                }
            }
        }
    }
}
