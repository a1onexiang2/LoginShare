package com.neilzheng.loginshare.helper

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import com.neilzheng.loginshare.LoginShare
import com.neilzheng.loginshare.share.PlatformConfig
import com.neilzheng.loginshare.share.Platforms
import com.neilzheng.loginshare.login.AuthEvent
import com.neilzheng.loginshare.utils.showToast
import com.neilzheng.loginshare.R
import org.greenrobot.eventbus.EventBus

/**
 * Created by Neil Zheng on 2017/7/25.
 */

internal class LoginHandler private constructor() {

    private object Inner {
        var instance = LoginHandler()
    }

    internal companion object {
        fun get(): LoginHandler = Inner.instance
    }

    internal fun login(activity: Activity, needUserInfo: Boolean, platform: Platforms) {
        when(platform) {
            Platforms.QQ, Platforms.QZONE -> {
                if (!PlatformConfig.isQqInstalled) {
                    throw RuntimeException("You must call LoginShare.initQq() first.")
                }
                if (QqHelper.isSsoAvailable()) {
                    QqHelper.get().login(activity, needUserInfo)
                } else {
                    showToast(LoginShare.appContext, R.string.text_login_qq_not_installed)
                }
            }
            Platforms.WECHAT, Platforms.MOMENTS -> {
                if (!PlatformConfig.isWechatInstalled) {
                    throw RuntimeException("You must call LoginShare.initWechat() first.")
                }
                if (WechatHelper.isSsoAvailable()) {
                    WechatHelper.get().login(activity, needUserInfo)
                } else {
                    showToast(LoginShare.appContext, R.string.text_login_wechat_not_installed)
                }
            }
            Platforms.WEIBO -> {
                if (!PlatformConfig.isWeiboInstalled) {
                    throw RuntimeException("You must call LoginShare.initWeibo() first.")
                }
                if (WeiboHelper.isSsoAvailable()) {
                    WeiboHelper.get().login(activity, needUserInfo)
                } else {
                    showToast(LoginShare.appContext, R.string.text_login_weibo_not_installed)
                }
            }
            else -> {
                throw RuntimeException("Current SDK version only support QQ, Wechat, Sina Weibo. " +
                        "If you require other platforms, contact me through e-mail \"a1onexiang2@qq.com\". " +
                        "I will try to update it.")
            }
        }
    }

    internal fun onActivityResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            val action = data.getStringExtra("key_action")
            if (TextUtils.equals("action_login", action)) {
                QqHelper.get().handleResultIntent(data)
            }
        } else {
            EventBus.getDefault().post(AuthEvent.generateCancelEvent())
        }
    }

    internal fun handleWechatResultIntent(intent: Intent) {
        WechatHelper.get().handleResultIntent(intent)
    }
}