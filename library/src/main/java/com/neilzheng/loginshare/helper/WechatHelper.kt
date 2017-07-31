package com.neilzheng.loginshare.helper

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import com.neilzheng.loginshare.LoginShare
import com.neilzheng.loginshare.share.PlatformConfig
import com.neilzheng.loginshare.share.ShareContent
import com.neilzheng.loginshare.login.WechatAuthResult
import com.neilzheng.loginshare.login.WechatUserInfoResult
import com.neilzheng.loginshare.login.AuthEvent
import com.neilzheng.loginshare.utils.HttpUtils
import com.neilzheng.loginshare.utils.ImageUtils
import com.tencent.mm.sdk.modelbase.BaseReq
import com.tencent.mm.sdk.modelbase.BaseResp
import com.tencent.mm.sdk.openapi.IWXAPI
import com.tencent.mm.sdk.openapi.WXAPIFactory
import com.tencent.mm.sdk.modelmsg.*
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.HashMap


/**
 * Created by Neil Zheng on 2017/7/26.
 */

internal class WechatHelper private constructor() : BaseHelper() {

    var needUserInfo: Boolean = false
    private val mApi: IWXAPI = WXAPIFactory.createWXAPI(LoginShare.appContext, PlatformConfig.wechatId)

    internal companion object {

        private val TAG = "WechatHelper"
        private val TYPE_WECHAT_FRIEND = 0
        private val TYPE_WECHAT_TIMELINE = 1

        /** Min supported version.  */
        private val MIN_SUPPORTED_VERSION = 0x21020001

        private val MAX_IMAGE_LENGTH = 32 * 1024
        private val DEFAULT_MAX_SIZE = 150

        private val URL_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token"
        private val URL_USER_INFO = "https://api.weixin.qq.com/sns/userinfo"

        fun get(): WechatHelper = WechatHelper.Inner.instance

        fun isSsoAvailable(): Boolean {
            return WechatHelper.get().mApi.isWXAppInstalled
        }

        fun isShareAvailable(): Boolean {
            return WechatHelper.get().mApi.wxAppSupportAPI >= MIN_SUPPORTED_VERSION
        }
    }

    private object Inner {
        var instance = WechatHelper()
    }

    internal fun login(activity: Activity, needUserInfo: Boolean) {
        this.needUserInfo = needUserInfo
        val request = SendAuth.Req()
        request.scope = PlatformConfig.wechatScope
        request.state = PlatformConfig.wechatState
        mApi.sendReq(request)
    }

    internal fun handleResultIntent(intent: Intent) {
        mApi.handleIntent(intent, object : IWXAPIEventHandler {
            override fun onResp(response: BaseResp) {
                handlerResponse(response)
            }

            override fun onReq(request: BaseReq) {
            }
        })
    }

    private fun handlerResponse(response: BaseResp) {
        when (response) {
            is SendAuth.Resp -> {
                when (response.errCode) {
                    BaseResp.ErrCode.ERR_OK -> {
                        if (!TextUtils.isEmpty(response.code)) {
                            requestToken(response.code)
                        } else {
                            postFailureEvent()
                        }
                    }
                    BaseResp.ErrCode.ERR_AUTH_DENIED -> {
                        postFailureEvent(response.errStr)
                    }
                    BaseResp.ErrCode.ERR_USER_CANCEL -> {
                        postCancelEvent()
                    }
                }
            }
        }
    }

    private fun requestToken(code: String) {
        val params = HashMap<String, String>()
        params.put("appid", PlatformConfig.wechatId)
        params.put("secret", PlatformConfig.wechatSecret)
        params.put("code", code)
        params.put("grant_type", "authorization_code")
        val url = HttpUtils.buildUrl(URL_TOKEN, params)
        HttpUtils.enqueue(Request.Builder().url(url).build(), object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                postFailureEvent(e.toString())
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body() ?: return
                    val result = WechatAuthResult(JSONObject(body.string()))
                    if (WechatHelper.get().needUserInfo) {
                        requestUserInfo(result.access_token, result.openid)
                    } else {
                        post(AuthEvent<WechatAuthResult>(result))
                    }
                } else {
                    postFailureEvent()
                }
            }
        })
    }

    private fun requestUserInfo(accessToken: String, openId: String) {
        val hashMap = HashMap<String, String>()
        hashMap.put("access_token", accessToken)
        hashMap.put("openid", openId)
        HttpUtils.enqueue(Request.Builder().url(HttpUtils.buildUrl(URL_USER_INFO, hashMap)).build(), object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                postFailureEvent(e.toString())
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body() ?: return
                    val userInfoResult = WechatUserInfoResult(JSONObject(body.string()))
                    post(AuthEvent<WechatUserInfoResult>(userInfoResult))
                } else {
                    postFailureEvent()
                }
            }
        })
    }

    internal fun doShareToFriend(activity: Activity, shareContent: ShareContent) {
        val msg = WXMediaMessage(WXWebpageObject(shareContent.url))
        if (TextUtils.isEmpty(shareContent.content)) {
            if (shareContent.filePathList != null && shareContent.filePathList!!.isNotEmpty()) {
                val imgObj = WXImageObject()
                imgObj.setImagePath(shareContent.filePathList!![0])
                msg.mediaObject = imgObj
            }
        } else {
            msg.description = shareContent.content
        }
        msg.title = shareContent.title
        msg.thumbData = ImageUtils.bitmapToBytes(shareContent.bitmap, true)
        val req = SendMessageToWX.Req()
        req.transaction = TYPE_WECHAT_FRIEND.toString() + System.currentTimeMillis().toString()
        req.message = msg
        req.scene = SendMessageToWX.Req.WXSceneSession
        mApi.sendReq(req)
    }

    internal fun doShareToMoments(activity: Activity, shareContent: ShareContent) {
        val msg = WXMediaMessage(WXWebpageObject(shareContent.url))
        if (TextUtils.isEmpty(shareContent.content)) {
            if (shareContent.filePathList != null && shareContent.filePathList!!.isNotEmpty()) {
                val imgObj = WXImageObject()
                imgObj.setImagePath(shareContent.filePathList!![0])
                msg.mediaObject = imgObj
            }
        } else {
            msg.description = shareContent.content
        }
        msg.title = shareContent.title
        msg.thumbData = ImageUtils.bitmapToBytes(shareContent.bitmap, true)
        val req = SendMessageToWX.Req()
        req.transaction = TYPE_WECHAT_TIMELINE.toString() + System.currentTimeMillis().toString()
        req.message = msg
        req.scene = SendMessageToWX.Req.WXSceneTimeline
        mApi.sendReq(req)
    }



}