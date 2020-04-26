package com.therealorange.notreddit.controllers

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.fragment.app.Fragment
import com.therealorange.notreddit.adapters.PhotosAdapter
import com.therealorange.notreddit.util.BottomNavigation
import kotlinx.android.synthetic.main.fragment_inbox.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object InboxController : FragmentController {
    private lateinit var mcontext: Fragment
    val images = listOf<String>("https://www.catster.com/wp-content/uploads/2015/06/google-cat-search-2014-03.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTGkiUL-DAKXj09OHfWNneHzKZvdOSo2nkFSNu4wUaf3I_ze1gf&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ_2KL1oaGklf_loKpT9uKrC1Atzm8UZPL93C7w9fvImK_qJ9aK&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTquQ4EHWRJGWARW4-Kfv_zcWChrN_Smzbzffb37IR2PTpCttNu&usqp=CAU")
    lateinit var photoviewadapter: PhotosAdapter

    override fun onCreateView(context: Fragment) {
        photoviewadapter = PhotosAdapter(context.requireContext(), mutableListOf())
        BottomNavigation.hide(false)
    }

    override fun onViewCreated(context: Fragment) {
        mcontext = context
        with(mcontext) {
            multiimageview.adapter = photoviewadapter
            GlobalScope.launch { images.forEach {
                val img = getBitmapFromURL(it)
                requireActivity().runOnUiThread {
                    if (img != null) {
                        photoviewadapter.addPhoto(img)
                    }
                }
            } }
        }
    }

    override fun restoreState() {
    }

    override fun onDestroyView() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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