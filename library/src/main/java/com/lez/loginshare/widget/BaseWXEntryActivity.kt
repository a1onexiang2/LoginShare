package com.lez.loginshare.widget

import com.lez.loginshare.LoginShare

/**
 * Created by Neil Zheng on 2017/7/26.
 */
open class BaseWXEntryActivity : android.app.Activity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent()
    }

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent()
    }

    private fun handleIntent() {
        LoginShare.handleWechatResultIntent(intent)
        finish()
    }
}