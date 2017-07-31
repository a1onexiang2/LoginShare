package com.vpngo.speed;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.lez.loginshare.share.*;
import com.lez.loginshare.share.ShareRecyclerAdapter;

/**
 * Created by Neil Zheng on 2017/7/31.
 */

public class CustomShareDialog extends BaseShareDialog {

    @Override
    public int styleResId() {
        return R.style.ShareDialog_Top;
    }

    @Override
    public int themeResId() {
        return R.style.ShareDialog_Top;
    }

    @Override
    public int layoutResId() {
        return config.getShareStyle().getCorner() ? R.layout.dialog_share_custom_corner : R.layout.dialog_share_custom;
    }

    @Override
    public void initDialog() {
        getDialog().getWindow().setGravity(Gravity.TOP);
    }

    @Override
    public void iniView() {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), config.getShareStyle().getSpan()));
        final ShareRecyclerAdapter adapter =
                new ShareRecyclerAdapter(config.getPlatformList());
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new com.lez.loginshare.share.BaseViewHolder.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                doShare(config.shareContent, adapter.getItem(position));
                dismiss();
            }
        });
    }
}
