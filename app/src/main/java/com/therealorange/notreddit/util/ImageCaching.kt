package com.therealorange.notreddit.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.therealorange.notreddit.MainActivity
import com.therealorange.notreddit.util.ImageUtils.readBitmap
import com.therealorange.notreddit.util.ImageUtils.writeBitmap
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import kotlin.concurrent.timerTask

object ImageCaching {
    lateinit var context: Context

    private const val RAM_CACHE_SIZE = 50
    private const val LOCAL_CACHE_SIZE = 300
    private const val NAME = "imgcache"
    private const val CACHE_DIR = "images_cached"

    private var lastAccessed = mutableMapOf<String, Long>()

    private val cachedImgMemory = mutableMapOf<String, Bitmap>()
    private val cachedImgStorage = mutableMapOf<String, File>()

    private var accessing = false
    private lateinit var cacheDir: File

    fun init(c: Context) {
        cacheDir = MainActivity.appContext.getDir(CACHE_DIR, Context.MODE_PRIVATE)
        if (cacheDir.listFiles() != null && cacheDir.listFiles()!!.isNotEmpty()) {
            cacheDir.listFiles()!!.forEach {
                lastAccessed[it.name] = System.currentTimeMillis()
                cachedImgStorage[it.name] = it
            }
        }
        context = c
        Timer().scheduleAtFixedRate(timerTask {
            GlobalScope.launch {
                reOrderFiles()
            }
        }, 2000, 5000)
    }

    fun imgCached(url: String) = lastAccessed.containsKey(UUIDv5.generateType5UUID(NAME, url).toString())

    fun getImg(url: String): Bitmap? {
        synchronized(accessing) {
            accessing = true
            val key = UUIDv5.generateType5UUID(NAME, url).toString()
            if (imgCached(url)) lastAccessed[key] = System.currentTimeMillis()
            if (cachedImgMemory.containsKey(key)) return cachedImgMemory[key]
            else if (cachedImgStorage.containsKey(key)) return cachedImgStorage[key]?.readBitmap()
            accessing = false
        }
        return null
    }

    fun cacheImg(url: String, bm: Bitmap) {
        synchronized(accessing) {
            accessing = true
            val key = UUIDv5.generateType5UUID(NAME, url).toString()
            if (!imgCached(url)) {
                lastAccessed[key] = System.currentTimeMillis()
                if (!cachedImgMemory.containsKey(key)) cachedImgMemory[key] = bm
            }
            accessing = false
        }
    }

    private fun reOrderFiles() {
        synchronized(accessing) {
            accessing = true
            val order = mutableListOf<Pair<String, Long>>()
            val remaining = mutableMapOf<String, Long>()
            lastAccessed.forEach { order.add(Pair(it.key, it.value)) }
            order.sortBy { it.second }
            order.forEachIndexed { index, pair ->
                if (index < RAM_CACHE_SIZE && !cachedImgMemory.containsKey(pair.first) && cachedImgStorage.containsKey(
                        pair.first
                    )
                ) {
                    cachedImgStorage[pair.first]?.readBitmap()?.let { it1 ->
                        cachedImgMemory.put(pair.first, it1)
                    }
                    cachedImgStorage[pair.first]?.delete()
                    cachedImgStorage.remove(pair.first)
                    remaining[pair.first] = pair.second
                } else if (index >= RAM_CACHE_SIZE && index < RAM_CACHE_SIZE + LOCAL_CACHE_SIZE && cachedImgMemory.containsKey(
                        pair.first
                    ) && !cachedImgStorage.containsKey(pair.first)
                ) {
                    cachedImgStorage[pair.first] = File(cacheDir, pair.first)
                    cachedImgMemory[pair.first]?.let { cachedImgStorage[pair.first]?.writeBitmap(it) }
                    cachedImgMemory.remove(pair.first)
                    remaining[pair.first] = pair.second
                } else if (index >= RAM_CACHE_SIZE + LOCAL_CACHE_SIZE && cachedImgMemory.containsKey(
                        pair.first
                    )
                ) {
                    cachedImgMemory.remove(pair.first)
                } else if (index >= RAM_CACHE_SIZE + LOCAL_CACHE_SIZE && cachedImgStorage.containsKey(
                        pair.first
                    )
                ) {
                    cachedImgStorage[pair.first]?.delete()
                    cachedImgStorage.remove(pair.first)
                }
            }
            lastAccessed = remaining
            accessing = false
        }
    }
}