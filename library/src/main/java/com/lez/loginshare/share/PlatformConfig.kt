package com.lez.loginshare.share

/**
 * Created by Neil Zheng on 2017/7/25.
 */

internal class PlatformConfig {

    internal companion object {

        internal lateinit var weiboKey: String get
        internal lateinit var weiboSecret: String get
        internal lateinit var weiboCallback: String get
        internal lateinit var weiboScope: String get
        internal lateinit var wechatId: String get
        internal lateinit var wechatSecret: String get
        internal lateinit var wechatScope: String get
        internal lateinit var wechatState: String get
        internal lateinit var qqId: String get
        internal lateinit var qqSecret: String get
        internal var isWeiboInstalled = false
        internal var isWechatInstalled = false
        internal var isQqInstalled = false

        fun initWeibo(key: String, secret: String, callback: String, scope: String) {
            PlatformConfig.weiboKey = key
            PlatformConfig.weiboSecret = secret
            PlatformConfig.weiboCallback = callback
            PlatformConfig.weiboScope = scope
            PlatformConfig.isWeiboInstalled = true
        }

        fun initWechat(key: String, secret: String, scope: String, state: String) {
            PlatformConfig.wechatId = key
            PlatformConfig.wechatSecret = secret
            PlatformConfig.wechatScope = scope
            PlatformConfig.wechatState = state
            PlatformConfig.isWechatInstalled = true
        }

        fun initQq(key: String, secret: String) {
            PlatformConfig.qqId = key
            PlatformConfig.qqSecret = secret
            PlatformConfig.isQqInstalled = true
        }

    }

}