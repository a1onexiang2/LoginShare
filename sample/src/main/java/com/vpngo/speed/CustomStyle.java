package com.vpngo.speed;

import com.neilzheng.loginshare.share.BaseStyle;
import com.neilzheng.loginshare.share.ShareConfig;
import com.neilzheng.loginshare.share.BaseShareDialog;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Neil Zheng on 2017/7/31.
 */

public class CustomStyle extends BaseStyle {

    public CustomStyle(boolean corner) {
        super(corner);
    }

    @NotNull
    @Override
    public String TAG() {
        return "Custom";
    }

    @NotNull
    @Override
    public BaseShareDialog getDialog(@NotNull ShareConfig config) {
        return BaseShareDialog.wrap(config, CustomShareDialog.class);
    }
}
