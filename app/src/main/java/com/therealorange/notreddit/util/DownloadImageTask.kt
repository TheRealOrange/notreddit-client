package com.therealorange.notreddit.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import kotlinx.io.InputStream
import java.net.URL

class DownloadImageTask(var bmImage: ImageView) : AsyncTask<String?, Void?, Bitmap?>() {
    override fun doInBackground(vararg urls: String?): Bitmap? {
        val urldisplay = urls[0]
        var mIcon11: Bitmap? = null
        try {
            if (!ImageCaching.imgCached(urldisplay!!)) {
                val input: InputStream = URL(urldisplay).openStream()
                mIcon11 = BitmapFactory.decodeStream(input)
                ImageCaching.cacheImg(urldisplay, mIcon11)
            } else {
                mIcon11 = ImageCaching.getImg(urldisplay)
            }
        } catch (e: Exception) {
            Log.e("Error", e.message!!)
            e.printStackTrace()
        }
        return mIcon11
    }

    override fun onPostExecute(result: Bitmap?) {
        bmImage.setImageBitmap(result)
    }

}