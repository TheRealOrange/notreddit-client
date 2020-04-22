package com.therealorange.notreddit.tabs

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import com.therealorange.notreddit.R
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class FragmentHome : FragmentObject(R.layout.fragment_home,"Home") {
    val images = listOf<String>("https://www.catster.com/wp-content/uploads/2015/06/google-cat-search-2014-03.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTGkiUL-DAKXj09OHfWNneHzKZvdOSo2nkFSNu4wUaf3I_ze1gf&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ_2KL1oaGklf_loKpT9uKrC1Atzm8UZPL93C7w9fvImK_qJ9aK&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTquQ4EHWRJGWARW4-Kfv_zcWChrN_Smzbzffb37IR2PTpCttNu&usqp=CAU")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("ARG_OBJECT") }?.apply {
            images.forEach { getBitmapFromURL(it)?.let { it1 -> multiimageview.addImage(it1) } }
        }
    }

    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}