package com.lez.loginshare.share

import android.graphics.Bitmap
import java.io.Serializable

/**
 * Created by Neil Zheng on 2017/7/26.
 */
class ShareContent : Serializable {

    internal var title: String? = null
    internal var content: String? = null
    internal var imageUrlList: ArrayList<String>? = null
    internal var filePathList: ArrayList<String>? = null
    internal var bitmap: Bitmap? = null
    internal var url: String? = null

    fun setTitle(title: String): ShareContent {
        this.title = title
        return this
    }

    fun setContent(content: String): ShareContent {
        this.content = content
        return this
    }

    fun setLocalImages(vararg filePath: String): ShareContent {
        if (filePath.isNotEmpty()) {
            filePathList = arrayListOf()
            filePath.forEach { filePathList!!.add(it) }
        }
        return this
    }

    fun setNetworkImages(vararg urls: String): ShareContent {
        if (urls.isNotEmpty()) {
            imageUrlList = arrayListOf()
            urls.forEach { imageUrlList!!.add(it) }
        }
        return this
    }

    fun setBitmap(bitmap: Bitmap): ShareContent {
        this.bitmap = bitmap
        return this
    }

    fun setUrl(url: String): ShareContent {
        this.url = url
        return this
    }

}