package com.neilzheng.loginshare.share

/**
 * Created by Neil Zheng on 2017/7/27.
 */
class ShareConfig : java.io.Serializable {

    companion object {
        @JvmStatic val serialVersionUID = 42L;
    }

    lateinit var shareContent: ShareContent
    var shareStyle: BaseStyle = BottomStyle()
    val platformList: ArrayList<SharePlatform> = arrayListOf()

    fun setPlatforms(vararg platforms: SharePlatform): ShareConfig {
        platformList.addAll(platforms)
        return this
    }

    fun setContent(content: ShareContent): ShareConfig {
        shareContent = content
        return this
    }

    fun setStyle(style: BaseStyle): ShareConfig {
        shareStyle = style
        return this
    }

}