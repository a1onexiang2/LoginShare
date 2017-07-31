package com.vpngo.speed;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lez.loginshare.LoginShare;
import com.lez.loginshare.share.BottomStyle;
import com.lez.loginshare.share.CenterStyle;
import com.lez.loginshare.share.DropdownStyle;
import com.lez.loginshare.share.FullscreenStyle;
import com.lez.loginshare.share.Platforms;
import com.lez.loginshare.login.QqAuthResult;
import com.lez.loginshare.login.QqUserInfoResult;
import com.lez.loginshare.share.ShareConfig;
import com.lez.loginshare.share.ShareContent;
import com.lez.loginshare.share.SharePlatform;
import com.lez.loginshare.login.WechatAuthResult;
import com.lez.loginshare.login.WechatUserInfoResult;
import com.lez.loginshare.login.WeiboAuthResult;
import com.lez.loginshare.login.WeiboUserInfoResult;
import com.lez.loginshare.login.AuthEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Neil Zheng on 2017/7/28.
 */

public class JavaActivity extends AppCompatActivity {

    public final static String TAG = JavaActivity.class.getName();

    private RecyclerView ssoRecyclerView;
    private SsoRecyclerAdapter ssoAdapter;
    private RecyclerView shareRecyclerView;
    private ShareRecyclerAdapter shareAdapter;
    private AppCompatButton shareBottom;
    private AppCompatButton shareCenter;
    private AppCompatButton shareDropdown;
    private AppCompatButton shareFullscreen;
    private AppCompatButton shareCustom;
    private AppCompatButton clear;
    private TextView result;
    private ShareContent shareContent;

    private String[] ssoArray = new String[]{"QQ 登录",
            "QQ 获取信息",
            "微信登录",
            "微信获取信息",
            "微博登录",
            "微博获取信息"};

    private SharePlatform[] shareArray = new SharePlatform[]{new SharePlatform(Platforms.QQ, "QQ"),
            new SharePlatform(Platforms.QZONE, "QQ空间"),
            new SharePlatform(Platforms.WECHAT, "微信好友"),
            new SharePlatform(Platforms.MOMENTS, "朋友圈"),
            new SharePlatform(Platforms.WEIBO, "新浪微博"),
            new SharePlatform(Platforms.SYSTEM, "系统分享"),
            new SharePlatform(Platforms.SYSTEM, "圆角模式")};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initView();
        initData();
        initListener();
        EventBus.getDefault().register(this);
        setTitle("JAVA 调用 SDK");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        ssoRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_sso);
        ssoRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        shareRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_share);
        shareRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        shareBottom = (AppCompatButton) findViewById(R.id.action_share_bottom);
        shareCenter = (AppCompatButton) findViewById(R.id.action_share_center);
        shareDropdown = (AppCompatButton) findViewById(R.id.action_share_dropdown);
        shareFullscreen = (AppCompatButton) findViewById(R.id.action_share_fullscreen);
        shareCustom = (AppCompatButton) findViewById(R.id.action_share_custom);
        clear = (AppCompatButton) findViewById(R.id.action_clear);
        result = (TextView) findViewById(R.id.result);
    }

    private void initData() {
        initShareContent();
        initSsoRecyclerView();
        initShareRecyclerView();
    }

    private void initShareContent() {
        shareContent = new ShareContent()
                .setTitle("title")
                .setContent("content")
                .setUrl("http://cn.bing.com")
                .setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setNetworkImages("https://pic2.zhimg.com/cc7b1709d_xs.jpg");
    }

    private void initSsoRecyclerView() {
        if (null == ssoAdapter) {
            ssoAdapter = new SsoRecyclerAdapter(ssoArray);
            ssoAdapter.setOnClickListener(new BaseViewHolder.onItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    doItemClick(position);
                }
            });
        }
        if (null == ssoRecyclerView.getAdapter()) {
            ssoRecyclerView.setAdapter(ssoAdapter);
        }
    }

    private void initShareRecyclerView() {
        if (null == shareAdapter) {
            shareAdapter = new ShareRecyclerAdapter(shareArray);
        }
        if (null == shareRecyclerView.getAdapter()) {
            shareRecyclerView.setAdapter(shareAdapter);
        }
    }

    private void initListener() {
        shareBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginShare.share(getThis(),
                        new ShareConfig()
                                .setContent(shareContent)
                                .setStyle(new BottomStyle(shareAdapter.isSelected(6)))
                                .setPlatforms(shareAdapter.getSelectedPlatforms()));
            }
        });
        shareCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginShare.share(getThis(),
                        new ShareConfig()
                                .setContent(shareContent)
                                .setStyle(new CenterStyle(shareAdapter.isSelected(6)))
                                .setPlatforms(shareAdapter.getSelectedPlatforms()));
            }
        });
        shareDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginShare.share(getThis(),
                        new ShareConfig()
                                .setContent(shareContent)
                                .setStyle(new DropdownStyle(shareAdapter.isSelected(6), shareDropdown, false))
                                .setPlatforms(shareAdapter.getSelectedPlatforms()));
            }
        });
        shareFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginShare.share(getThis(),
                        new ShareConfig()
                                .setContent(shareContent)
                                .setStyle(new FullscreenStyle(shareAdapter.isSelected(6)))
                                .setPlatforms(shareAdapter.getSelectedPlatforms()));
            }
        });
        shareCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginShare.share(getThis(),
                        new ShareConfig()
                                .setContent(shareContent)
                                .setStyle(new CustomStyle(shareAdapter.isSelected(6)))
                                .setPlatforms(shareAdapter.getSelectedPlatforms()));
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("");
            }
        });
    }

    private void doItemClick(int position) {
        switch (position) {
            case 0:
                LoginShare.login(this, Platforms.QQ);
                break;
            case 1:
                LoginShare.login(this, Platforms.QQ, true);
                break;
            case 2:
                LoginShare.login(this, Platforms.WECHAT);
                break;
            case 3:
                LoginShare.login(this, Platforms.WECHAT, true);
                break;
            case 4:
                LoginShare.login(this, Platforms.WEIBO);
                break;
            case 5:
                LoginShare.login(this, Platforms.WEIBO, true);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public <T> void onEvent(AuthEvent<T> event) {
        if (event.isSuccess()) {
            T data = event.getData();
            if (data instanceof Void) {
                logD("something void");
                return;
            }
            if (data instanceof QqAuthResult) {
                //TODO handle the QqAuthResult
            } else if (data instanceof WechatAuthResult) {
                //TODO handle the WechatAuthResult
            } else if (data instanceof WeiboAuthResult) {
                //TODO handle the WeiboAuthResult
            } else if (data instanceof QqUserInfoResult) {
                //TODO handle the QqUserInfoResult
            } else if (data instanceof WechatUserInfoResult) {
                //TODO handle the WechatUserInfoResult
            } else if (data instanceof WeiboUserInfoResult) {
                //TODO handle the WeiboUserInfoResult
            } else if (data instanceof Throwable) {
                //TODO handle the Throwable
            }
            updateData(data);
        } else if (event.isCancel()) {
            updateData("授权取消");
        } else if (event.isFailure()) {
            updateData("授权失败");
        } else if (event.isError()) {
            updateData("授权错误");
        }
    }

    private <T> void updateData(T data) {
        logD(data.toString());
        result.setText(data.toString());
    }

    private AppCompatActivity getThis() {
        return this;
    }

    private void logD(String message) {
        Log.d(TAG, message);
    }

}
