package com.neilzheng.loginshare.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.neilzheng.loginshare.LoginShare
import com.neilzheng.loginshare.share.PlatformConfig
import com.neilzheng.loginshare.login.QqAuthResult
import com.neilzheng.loginshare.login.QqUserInfoResult
import com.neilzheng.loginshare.share.ShareContent
import com.neilzheng.loginshare.login.AuthEvent
import com.tencent.connect.UserInfo
import com.tencent.connect.auth.QQToken
import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzoneShare
import com.tencent.open.utils.SystemUtils
import com.tencent.open.utils.Util
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.json.JSONObject

/**
 * Created by Neil Zheng on 2017/7/25.
 */

internal class QqHelper private constructor() : BaseHelper() {

    var needUserInfo: Boolean = false
    private val mApi: Tencent = Tencent.createInstance(PlatformConfig.qqId, LoginShare.appContext)

    internal companion object {

        private val TAG = "QqHelper"
        private val TYPE_QQ = 0
        private val TYPE_QZONE = 1

        fun get(): QqHelper = Inner.instance

        fun isSsoAvailable(context: Context = LoginShare.appContext): Boolean {
            if (Util.isTablet(context) && getAppVersionName(context, "com.tencent.minihd.qq") != null) {
                return true
            } else {
                return getAppVersionName(context, "com.tencent.mobileqq") != null
                        && checkMobileQq(context)
            }
        }

        fun isShareAvailable(context: Context = LoginShare.appContext): Boolean {
            return checkMobileQq(context)
        }


        fun getAppVersionName(context: Context, appName: String): String? {
            val pm = context.packageManager
            try {
                val packageInfo = pm.getPackageInfo(appName, PackageManager.GET_SIGNATURES)
                return packageInfo.versionName
            } catch (ignored: PackageManager.NameNotFoundException) {
            }
            return null
        }

        fun checkMobileQq(context: Context): Boolean {
            val versionName = getAppVersionName(context, "com.tencent.mobileqq") ?: return false
            try {
                val versions = versionName.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                val main = Integer.parseInt(versions[0])
                val inner = Integer.parseInt(versions[1])
                return main > 4 || main == 4 && inner >= 1
            } catch (ignored: Exception) {
            }
            return false
        }
    }

    private object Inner {
        var instance = QqHelper()
    }

    internal fun login(activity: Activity, needUserInfo: Boolean) {
        this.needUserInfo = needUserInfo
        mApi.login(activity, "all", null)
    }

    internal fun handleResultIntent(data: Intent) {
        val responseBody = data.getIntExtra("key_error_code", 0)
        if (responseBody == 0) {
            val responseInfo = data.getStringExtra("key_response")
            if (responseInfo != null) {
                val result = QqAuthResult(Util.parseJson(responseInfo))
                if (QqHelper.get().needUserInfo) {
                    getUserInfo(result.access_token, result.expires_in, result.openid)
                } else {
                    post(AuthEvent<QqAuthResult>(result))
                }
            } else {
                postFailureEvent()
            }
        } else {
            postFailureEvent()
        }
    }

    private fun getUserInfo(accessToken: String, expiresIn: Long, openId: String) {
        val qqToken = QQToken(PlatformConfig.qqId)
        qqToken.setAccessToken(accessToken, expiresIn.toString())
        qqToken.openId = openId
        UserInfo(LoginShare.appContext, qqToken).getUserInfo(object : IUiListener {
            override fun onComplete(response: Any) {
                val result = QqUserInfoResult(JSONObject(response.toString()))
                result.open_id = qqToken.openId
                post(AuthEvent<QqUserInfoResult>(result))
            }

            override fun onError(uiError: UiError) {
                postFailureEvent(uiError.errorMessage)
            }

            override fun onCancel() {
                postCancelEvent()
            }
        })
    }

    internal fun doShareToQq(activity: Activity, shareContent: ShareContent) {
        val params = Bundle()
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareContent.title)
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent.content)
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.url)
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                if (shareContent.content != null) QQShare.SHARE_TO_QQ_TYPE_DEFAULT else QQShare.SHARE_TO_QQ_TYPE_IMAGE)
        if (shareContent.filePathList != null && shareContent.filePathList!!.isNotEmpty()) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareContent.filePathList!![0])
        } else if (shareContent.imageUrlList != null && shareContent.imageUrlList!!.isNotEmpty()) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareContent.imageUrlList!![0])
        }
        mApi.shareToQQ(activity, params, null)
    }

    internal fun doShareToQZone(activity: Activity, shareContent: ShareContent) {
        val params = Bundle()
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT)
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareContent.title)
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent.content)
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareContent.url)
        if (shareContent.filePathList != null && shareContent.filePathList!!.isNotEmpty()) {
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareContent.filePathList)
        } else if (shareContent.imageUrlList != null && shareContent.imageUrlList!!.isNotEmpty()) {
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, shareContent.imageUrlList)
        }
        mApi.shareToQzone(activity, params, null)
    }
}