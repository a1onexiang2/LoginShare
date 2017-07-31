package com.vpngo.speed

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.neilzheng.loginshare.LoginShare
import com.neilzheng.loginshare.login.*
import com.neilzheng.loginshare.share.*
import com.neilzheng.loginshare.utils.logD
import com.neilzheng.loginshare.utils.logE
import kotlinx.android.synthetic.main.activity_demo.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class KotlinActivity : AppCompatActivity() {

    private val ssoArray = arrayOf("QQ 登录",
            "QQ 获取信息",
            "微信登录",
            "微信获取信息",
            "微博登录",
            "微博获取信息")

    private val shareArray = arrayOf(SharePlatform(Platforms.QQ, "QQ"),
            SharePlatform(Platforms.QZONE, "QQ空间"),
            SharePlatform(Platforms.WECHAT, "微信好友"),
            SharePlatform(Platforms.MOMENTS, "朋友圈"),
            SharePlatform(Platforms.WEIBO, "新浪微博"),
            SharePlatform(Platforms.SYSTEM, "系统分享"),
            SharePlatform(Platforms.SYSTEM, "圆角模式"))

    private lateinit var shareAdapter: ShareRecyclerAdapter

    private lateinit var shareContent: ShareContent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this@KotlinActivity)
        setContentView(R.layout.activity_demo)
        initData()
        initListener()
        title = "Kotlin 调用 SDK"
    }

    private fun initData() {
        initShareContent()
        initSsoRecyclerView()
        initShareRecyclerView()
    }

    private fun initShareContent() {
        shareContent = ShareContent()
                .setTitle("title")
                .setContent("content")
                .setUrl("http://cn.bing.com")
                .setBitmap(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setNetworkImages("https://pic2.zhimg.com/cc7b1709d_xs.jpg")
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this@KotlinActivity)
    }

    private fun initSsoRecyclerView() {
        recyclerView_sso.layoutManager = GridLayoutManager(this@KotlinActivity, 3)
        val adapter = SsoRecyclerAdapter(ssoArray)
        recyclerView_sso.adapter = adapter
        adapter.onClickListener = object : BaseViewHolder.onItemClickListener {
            override fun onItemClick(position: Int) {
                doItemClick(position)
            }
        }
    }

    private fun initShareRecyclerView() {
        recyclerView_share.layoutManager = GridLayoutManager(this@KotlinActivity, 3)
        shareAdapter = ShareRecyclerAdapter(shareArray)
        recyclerView_share.adapter = shareAdapter
    }

    private fun doItemClick(position: Int) {
        when (position) {
            0 -> LoginShare.login(this@KotlinActivity, Platforms.QQ)
            1 -> LoginShare.login(this@KotlinActivity, Platforms.QQ, true)
            2 -> LoginShare.login(this@KotlinActivity, Platforms.WECHAT)
            3 -> LoginShare.login(this@KotlinActivity, Platforms.WECHAT, true)
            4 -> LoginShare.login(this@KotlinActivity, Platforms.WEIBO)
            5 -> LoginShare.login(this@KotlinActivity, Platforms.WEIBO, true)
        }
    }

    private fun initListener() {
        action_share_bottom.setOnClickListener {
            LoginShare.share(this@KotlinActivity,
                    ShareConfig()
                            .setContent(shareContent)
                            .setStyle(BottomStyle(shareAdapter.isSelected(6)))
                            .setPlatforms(*shareAdapter.getSelectedPlatforms()))
        }
        action_share_center.setOnClickListener {
            LoginShare.share(this@KotlinActivity,
                    ShareConfig()
                            .setContent(shareContent)
                            .setStyle(CenterStyle(shareAdapter.isSelected(6)))
                            .setPlatforms(*shareAdapter.getSelectedPlatforms()))
        }
        action_share_dropdown.setOnClickListener {
            LoginShare.share(this@KotlinActivity,
                    ShareConfig()
                            .setContent(shareContent)
                            .setStyle(DropdownStyle(shareAdapter.isSelected(6), action_share_dropdown))
                            .setPlatforms(*shareAdapter.getSelectedPlatforms()))
        }
        action_share_fullscreen.setOnClickListener {
            LoginShare.share(this@KotlinActivity,
                    ShareConfig()
                            .setContent(shareContent)
                            .setStyle(FullscreenStyle(shareAdapter.isSelected(6)))
                            .setPlatforms(*shareAdapter.getSelectedPlatforms()))
        }
        action_share_custom.setOnClickListener {
            LoginShare.share(this@KotlinActivity,
                    ShareConfig()
                            .setContent(shareContent)
                            .setStyle(CustomStyle(shareAdapter.isSelected(6)))
                            .setPlatforms(*shareAdapter.getSelectedPlatforms()))
        }
        action_clear.setOnClickListener {
            result.text = ""
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun <T> onEvent(event: AuthEvent<T>) {
        if (event.isSuccess()) {
            val data = event.data
            if (data is Void) {
                logE("something void")
                return
            }
            when (data) {
                is QqAuthResult -> {
                    //TODO handle the QqAuthResult
                }
                is WechatAuthResult -> {
                    //TODO handle the WechatAuthResult
                }
                is WeiboAuthResult -> {
                    //TODO handle the WeiboAuthResult
                }
                is QqUserInfoResult -> {
                    //TODO handle the QqUserInfoResult
                }
                is WechatUserInfoResult -> {
                    //TODO handle the WechatUserInfoResult
                }
                is WeiboUserInfoResult -> {
                    //TODO handle the WeiboUserInfoResult
                }
                is Throwable -> {
                    //TODO handle the Throwable
                }
            }
            updateData(data)
        } else if (event.isCancel()) {
            updateData("授权取消")
        } else if (event.isFailure()) {
            updateData("授权失败")
        } else if (event.isError()) {
            updateData("授权错误")
        }
    }

    private fun <T> updateData(data: T) {
        logD(data.toString())
        result.text = data.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LoginShare.onActivityResult(requestCode, resultCode, data)
        Log.e("123", "123")
    }
}
