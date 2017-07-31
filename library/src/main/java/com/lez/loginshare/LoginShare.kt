package com.lez.loginshare

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.lez.loginshare.share.PlatformConfig
import com.lez.loginshare.share.Platforms
import com.lez.loginshare.helper.LoginHandler
import com.lez.loginshare.share.ShareConfig
import com.lez.loginshare.helper.ShareHandler

/**
 * Created by Neil Zheng on 2017/7/25.
 */
class LoginShare private constructor() {

    companion object {

        internal lateinit var appContext: Context
        internal lateinit var loginHandler: LoginHandler
        internal lateinit var shareHandler: ShareHandler

        @JvmStatic fun init(context: Context) {
            appContext = context.applicationContext
            loginHandler = LoginHandler.get()
            shareHandler = ShareHandler.get()
        }

        @JvmStatic fun initQq(key: String, secret: String) {
            PlatformConfig.initQq(key, secret)
        }

        @JvmStatic fun initWeibo(key: String, secret: String, callback: String, scope: String) {
            PlatformConfig.initWeibo(key, secret, callback, scope)
        }

        @JvmStatic fun initWechat(key: String, secret: String, scope: String, state: String) {
            PlatformConfig.initWechat(key, secret, scope, state)
        }

        @JvmStatic fun login(activity: Activity, platform: Platforms) {
            login(activity, platform, false)
        }

        @JvmStatic fun login(activity: Activity, platform: Platforms, needUserInfo: Boolean = false) {
            loginHandler.login(activity, needUserInfo, platform)
        }

        @JvmStatic fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            if (requestCode == 11101) { // QQ SSO requestCode
                loginHandler.onActivityResult(resultCode, data)
            }
            if (requestCode == 10103 || requestCode == 10104) { // QQ/QZone Share requestCode
                shareHandler.onActivityResult(resultCode, data)
            }
        }

        internal fun handleWechatResultIntent(intent: Intent) {
            loginHandler.handleWechatResultIntent(intent)
        }

        @JvmStatic fun share(activity: AppCompatActivity, config: ShareConfig) {
            shareHandler.share(activity, config)
        }

    }

}