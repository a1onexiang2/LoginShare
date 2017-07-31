package com.vpngo.speed

import android.app.Application
import com.neilzheng.loginshare.LoginShare

/**
 * Created by Neil Zheng on 2017/7/25.
 */
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        LoginShare.init(this@MyApp)
        LoginShare.initQq("1105902421", "IH7Ku1u2ixfv0MFe")
        LoginShare.initWechat("wxdfebf35e3dc70a4f", "e936bd90715b71f986a4fe65da4941b3", "snsapi_userinfo", "")
        LoginShare.initWeibo("3336234770", "9f25c3545e2e19b1d832d1ada0a4699c", "https://api.weibo.com/oauth2/default.html", "")
    }
}