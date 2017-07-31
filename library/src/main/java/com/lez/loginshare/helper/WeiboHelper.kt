package com.lez.loginshare.helper

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import com.lez.loginshare.LoginShare
import com.lez.loginshare.share.PlatformConfig
import com.lez.loginshare.share.ShareContent
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.auth.WeiboAuthListener
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.sina.weibo.sdk.exception.WeiboException
import com.lez.loginshare.login.WeiboAuthResult
import com.lez.loginshare.login.WeiboUserInfoResult
import com.lez.loginshare.login.AuthEvent
import com.lez.loginshare.utils.HttpUtils
import com.lez.loginshare.utils.ImageUtils
import com.sina.weibo.sdk.api.ImageObject
import com.sina.weibo.sdk.api.TextObject
import com.sina.weibo.sdk.api.WebpageObject
import com.sina.weibo.sdk.api.WeiboMultiMessage
import com.sina.weibo.sdk.api.share.IWeiboShareAPI
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest
import com.sina.weibo.sdk.api.share.WeiboShareSDK
import com.sina.weibo.sdk.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.HashMap

/**
 * Created by Neil Zheng on 2017/7/26.
 */

internal class WeiboHelper private constructor() : BaseHelper() {

    private lateinit var mApi: SsoHandler
    private var mShareApi: IWeiboShareAPI = WeiboShareSDK.createWeiboAPI(LoginShare.appContext, PlatformConfig.weiboKey)

    init {
        mShareApi.registerApp()
    }

    var needUserInfo: Boolean = false

    internal companion object {

        private val TAG = "WeiboHelper"

        private val URL_USER_INFO = "https://api.weibo.com/2/users/show.json"

        fun get(): WeiboHelper = WeiboHelper.Inner.instance

        /**
         * 微博不需要依赖客户端
         * @return is installed
         */
        fun isSsoAvailable(): Boolean {
            return true
        }

        fun isShareAvailable(): Boolean {
            return WeiboHelper.get().mShareApi.isWeiboAppInstalled;
        }
    }

    private object Inner {
        var instance = WeiboHelper()
    }

    internal fun login(activity: Activity, needUserInfo: Boolean) {
        this.needUserInfo = needUserInfo
        initApi(activity)
        mApi.authorize(object : WeiboAuthListener {
            override fun onWeiboException(error: WeiboException) {
                postFailureEvent(error.toString())
            }

            override fun onComplete(values: Bundle) {
                val result = WeiboAuthResult(
                        values.getString("uid"),
                        values.getString("access_token"),
                        values.getString("expires_in"),
                        values.getString("remind_in"),
                        values.getString("refresh_token")
                )
                if (WeiboHelper.get().needUserInfo) {
                    requestUserInfo(result.uid, result.access_token)
                } else {
                    post(AuthEvent<WeiboAuthResult>(result))
                }
            }

            override fun onCancel() {
                postCancelEvent()
            }
        })
    }

    internal fun doShare(activity: Activity, shareContent: ShareContent) {
        val weiboMessage = WeiboMultiMessage()
        if (!TextUtils.isEmpty(shareContent.content)) {
            weiboMessage.textObject = generateTextObject(shareContent.content!!)
        }
        if (shareContent.bitmap != null) {
            weiboMessage.imageObject = generateImageObject(shareContent.bitmap!!)
        } else if (shareContent.filePathList != null && shareContent.filePathList!!.isNotEmpty()) {
            weiboMessage.imageObject = generateImageObject(shareContent.filePathList!![0])
        }
        val request = SendMultiMessageToWeiboRequest()
        request.transaction = System.currentTimeMillis().toString()
        request.multiMessage = weiboMessage
        mShareApi.sendRequest(activity, request)
    }

    private fun requestUserInfo(uid: String, accessToken: String) {
        val params = HashMap<String, String>()
        params.put("uid", uid)
        params.put("access_token", accessToken)
        params.put("source", PlatformConfig.weiboKey)
        val url = HttpUtils.buildUrl(URL_USER_INFO, params)
        val request = Request.Builder().url(url).build()
        HttpUtils.enqueue(request, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                postFailureEvent(e.toString())
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body() ?: return
                    val userInfoResult = WeiboUserInfoResult(JSONObject(body.string()))
                    post(AuthEvent<WeiboUserInfoResult>(userInfoResult))
                } else {
                    postFailureEvent()
                }
            }
        })
    }

    private fun initApi(activity: Activity) {
        mApi = SsoHandler(activity,
                AuthInfo(activity, PlatformConfig.weiboKey, PlatformConfig.weiboCallback, PlatformConfig.weiboScope))
    }

    private fun generateTextObject(content: String): TextObject {
        val text = TextObject()
        text.text = content
        return text
    }

    private fun generateImageObject(imagePath: String): ImageObject {
        val image = ImageObject()
        val bitmap = ImageUtils.getBitmapFromFile(imagePath)
        if (bitmap != null) {
            image.setImageObject(bitmap)
        }
        return image
    }

    private fun generateImageObject(bitmap: Bitmap?): ImageObject {
        val image = ImageObject()
        if (bitmap != null) {
            image.setImageObject(bitmap)
        }
        return image
    }

    private fun generateWebPageObject(title: String, des: String, url: String, thumbnail: Bitmap): WebpageObject {
        val web = WebpageObject()
        web.identify = Utility.generateGUID()
        web.title = title
        web.description = des
        web.setThumbImage(thumbnail)
        web.actionUrl = url
        web.defaultText = des
        return web
    }

}