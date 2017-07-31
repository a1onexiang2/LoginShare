package com.neilzheng.loginshare.share

import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.ViewGroup
import com.neilzheng.loginshare.R
import kotlinx.android.synthetic.main.dialog_share_fullscreen.*

/**
 * Created by Neil Zheng on 2017/7/26.
 */
class FullscreenShareDialog : BaseShareDialog() {

    override fun styleResId(): Int = R.style.ShareDialog_Fullscreen

    override fun themeResId(): Int = R.style.ShareDialog_Fullscreen

    override fun layoutResId(): Int {
        return if (config.shareStyle.corner) R.layout.dialog_share_fullscreen_corner else R.layout.dialog_share_fullscreen
    }

    override fun initDialog() {
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window.setGravity(Gravity.CENTER)
    }

    override fun iniView() {
        recyclerView.layoutManager = GridLayoutManager(context, config.shareStyle.span)
        val adapter = ShareRecyclerAdapter(config.platformList)
        recyclerView.adapter = adapter
        adapter.onClickListener = object : BaseViewHolder.onItemClickListener {
            override fun onItemClick(position: Int) {
                doShare(config.shareContent, adapter.getItem(position))
                dismiss()
            }
        }
    }
}