package com.lez.loginshare.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Created by Neil Zheng on 2017/7/26.
 */
object ImageUtils {

    private val TAG = "ImageUtils"

    /**
     * Convert Bitmap to byte[]
     * @param bitmap      the source bitmap
     * @param needRecycle need recycle
     * @return byte[]
     */
    fun bitmapToBytes(bitmap: Bitmap?, needRecycle: Boolean): ByteArray {
        if(bitmap == null) {
            return ByteArray(0)
        }
        val output = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
        if (needRecycle) {
            bitmap.recycle()
        }
        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
        return result
    }

    /**
     * Scale bitmap with width and height.
     * @param bitmap the source bitmap
     * @param w      the width
     * @param h      the height
     * @return the bitmap
     */
    fun zoom(bitmap: Bitmap?, w: Int, h: Int): Bitmap? {
        if (null == bitmap) {
            return null
        }
        try {
            val scaleWidth = w * 1.0f / bitmap.width
            val scaleHeight = h * 1.0f / bitmap.height
            val matrix = Matrix()
            matrix.postScale(scaleWidth, scaleHeight)
            val result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565)
            val canvas = Canvas(result)
            canvas.drawBitmap(bitmap, matrix, null)
            return result
        } catch (e: OutOfMemoryError) {
            return null
        }

    }

    /**
     * Get bitmap from file with the path.
     * @param path the file path
     * @return the bitmap
     */
    fun getBitmapFromFile(path: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val file = File(path)
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(path)
            }
        } catch (e: OutOfMemoryError) {
            Log.e(TAG, e.message)
        }
        return bitmap
    }
}