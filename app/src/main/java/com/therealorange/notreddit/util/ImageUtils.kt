package com.therealorange.notreddit.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.math.roundToInt
import kotlin.math.sqrt

object ImageUtils {
    fun encodeToBase64(image: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.getEncoder().encodeToString(b)
    }

    fun decodeBase64(input: String?): Bitmap? {
        val decodedByte: ByteArray = Base64.getDecoder().decode(input)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }

    fun scaleBitmap(bm: Bitmap): Bitmap {
        val MAX_BYTES = 50000000
        if (bm.byteCount > MAX_BYTES) {
            val ratio = sqrt(bm.byteCount.toDouble()/MAX_BYTES)
            val newWidth = bm.height.toDouble()/ratio
            val newHeight = bm.width.toDouble()/ratio
            return Bitmap.createScaledBitmap(bm, newWidth.roundToInt(), newHeight.roundToInt(), true)
        }
        return bm
    }

    fun File.writeBitmap(bitmap: Bitmap) {
        outputStream().use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
        }
    }

    fun File.readBitmap(): Bitmap {
        inputStream().use { input ->
            return BitmapFactory.decodeStream(input)
        }
    }
}