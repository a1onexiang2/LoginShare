package com.lez.loginshare.share

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lez.loginshare.share.ShareConfig
import com.lez.loginshare.share.ShareContent
import com.lez.loginshare.share.SharePlatform
import com.lez.loginshare.helper.ShareHandler

/**
 * Created by Neil Zheng on 2017/7/27.
 */
abstract class BaseShareDialog : android.support.v4.app.DialogFragment() {

    protected lateinit var config: com.lez.loginshare.share.ShareConfig

    companion object {
        val KEY_SERIALIZABLE = "Serializable"

        @JvmStatic fun <T : com.lez.loginshare.share.BaseShareDialog> wrap(config: com.lez.loginshare.share.ShareConfig, dialogClass: Class<T>): T {
            val dialog = dialogClass.newInstance()
            val arguments = android.os.Bundle()
            arguments.putSerializable(com.lez.loginshare.share.BaseShareDialog.Companion.KEY_SERIALIZABLE, config)
            dialog.arguments = arguments
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(styleResId(), themeResId())
        config = arguments.getSerializable(com.lez.loginshare.share.BaseShareDialog.Companion.KEY_SERIALIZABLE) as com.lez.loginshare.share.ShareConfig
    }

    abstract fun styleResId(): Int

    abstract fun themeResId(): Int

    abstract fun layoutResId(): Int

    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View {
        return inflater.inflate(layoutResId(), container)
    }

    override fun onActivityCreated(savedInstanceState: android.os.Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDialog()
        iniView()
    }

    abstract fun initDialog()

    abstract fun iniView()

    fun doShare(shareContent: com.lez.loginshare.share.ShareContent, sharePlatform: com.lez.loginshare.share.SharePlatform) {
        com.lez.loginshare.helper.ShareHandler.Companion.get().doShare(shareContent, sharePlatform)
    }

}