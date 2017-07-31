package com.neilzheng.loginshare.utils

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * Created by Neil Zheng on 2017/7/26.
 */
object HttpUtils {

    private val mOkHttpClient = OkHttpClient()

    /**
     * Request async.
     * @param request  the request object
     * @param callback the callback
     */
    fun enqueue(request: Request, callback: Callback) {
        mOkHttpClient.newCall(request).enqueue(callback)
    }

    /**
     * encode param with UTF-8
     * @param params the params
     * @return the encode string
     */
    fun encodeParams(params: Map<String, String>): String {
        val encodedParams = StringBuilder()
        val paramsEncoding = "UTF-8"
        try {
            val entrySet = params.entries
            val size = entrySet.size
            var index = 0
            for ((key, value) in entrySet) {
                encodedParams.append(URLEncoder.encode(key, paramsEncoding))
                encodedParams.append('=')
                encodedParams.append(URLEncoder.encode(value, paramsEncoding))
                if (++index < size) {
                    encodedParams.append('&')
                }
            }
            return encodedParams.toString()
        } catch (uee: UnsupportedEncodingException) {
            throw RuntimeException("Encoding not supported: " + paramsEncoding, uee)
        }

    }

    /**
     * Build imageUrlList with params, which can auto encode params.
     * @param url    the source imageUrlList
     * @param params the request params
     * @return the imageUrlList
     */
    fun buildUrl(url: String, params: Map<String, String>): String {
        var urlStr = url
        if (!urlStr.contains("?")) {
            urlStr += '?'
        }
        urlStr += encodeParams(params)
        return urlStr
    }
}
