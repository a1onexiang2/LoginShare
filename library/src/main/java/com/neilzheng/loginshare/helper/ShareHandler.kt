package com.neilzheng.loginshare.helper

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.neilzheng.loginshare.LoginShare
import com.neilzheng.loginshare.utils.showToast
import com.neilzheng.loginshare.R
import com.neilzheng.loginshare.share.*

/**
 * Created by Neil Zheng on 2017/7/25.
 */

internal class ShareHandler {

    private lateinit var activity: Activity

    private object Inner {
        var instance = ShareHandler()
    }

    internal companion object {
        fun get(): ShareHandler {
            return Inner.instance

        }
    }

    internal fun share(activity: AppCompatActivity, config: ShareConfig) {
        this.activity = activity
        when (config.platformList.size) {
            0 -> {
                doSystemShare(config.shareContent)
            }
            1 -> {
                doShare(config.shareContent, config.platformList[0])
            }
            else -> {
                config.shareStyle.getDialog(config).show(activity.supportFragmentManager, config.shareStyle.TAG())
            }
        }
    }

    internal fun doShare(shareContent: ShareContent, sharePlatform: SharePlatform) {
        val type = sharePlatform.type
        when (type) {
            Platforms.QQ -> {
                if (!PlatformConfig.isQqInstalled) {
                    throw RuntimeException("You must call LoginShare.initQq() first.")
                }
                if (QqHelper.isShareAvailable()) {
                    QqHelper.get().doShareToQq(activity, shareContent)
                } else {
                    showToast(LoginShare.appContext, R.string.text_share_qq_not_installed)
                }
            }
            Platforms.QZONE -> {
                if (!PlatformConfig.isQqInstalled) {
                    throw RuntimeException("You must call LoginShare.initQq() first.")
                }
                if (QqHelper.isShareAvailable()) {
                    QqHelper.get().doShareToQZone(activity, shareContent)
                } else {
                    showToast(LoginShare.appContext, R.string.text_share_qq_not_installed)
                }
            }
            Platforms.WECHAT -> {
                if (!PlatformConfig.isWechatInstalled) {
                    throw RuntimeException("You must call LoginShare.initWechat() first.")
                }
                if (WechatHelper.isShareAvailable()) {
                    WechatHelper.get().doShareToFriend(activity, shareContent)
                } else {
                    showToast(LoginShare.appContext, R.string.text_share_wechat_not_installed)
                }
            }
            Platforms.MOMENTS -> {
                if (!PlatformConfig.isWechatInstalled) {
                    throw RuntimeException("You must call LoginShare.initWechat() first.")
                }
                if (WechatHelper.isShareAvailable()) {
                    WechatHelper.get().doShareToMoments(activity, shareContent)
                } else {
                    showToast(LoginShare.appContext, R.string.text_share_wechat_not_installed)
                }
            }
            Platforms.WEIBO -> {
                if (!PlatformConfig.isWeiboInstalled) {
                    throw RuntimeException("You must call LoginShare.initWeibo() first.")
                }
                if (WeiboHelper.isShareAvailable()) {
                    WeiboHelper.get().doShare(activity, shareContent)
                } else {
                    showToast(LoginShare.appContext, R.string.text_share_weibo_not_installed)
                }
            }
            Platforms.COPY -> {
                doCopy(shareContent)
            }
            Platforms.SYSTEM -> {
                doSystemShare(shareContent)
            }
        }
    }

    private fun doCopy(shareContent: ShareContent) {
        val manager = (activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
        manager.primaryClip = ClipData.newPlainText(shareContent.title, shareContent.content)
    }

    private fun doSystemShare(shareContent: ShareContent) {
        var intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, shareContent.title)
        intent.putExtra(Intent.EXTRA_TEXT, shareContent.content)
        intent = Intent.createChooser(intent, activity.resources.getString(R.string.text_share_to))
        activity.startActivity(intent)
    }

    internal fun onActivityResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
        } else {
        }
    }

}