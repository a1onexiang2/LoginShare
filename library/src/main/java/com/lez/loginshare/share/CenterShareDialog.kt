package com.lez.loginshare.share

import android.view.Gravity
import android.view.ViewGroup
import com.lez.loginshare.R
import kotlinx.android.synthetic.main.dialog_share_center.*

/**
 * Created by Neil Zheng on 2017/7/26.
 */
class CenterShareDialog : BaseShareDialog() {

    override fun styleResId(): Int = R.style.ShareDialog_Center

    override fun themeResId(): Int = R.style.ShareDialog_Center

    override fun layoutResId(): Int {
        return if (config.shareStyle.corner) R.layout.dialog_share_center_corner else R.layout.dialog_share_center
    }

    override fun initDialog() {
        dialog.window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setGravity(Gravity.CENTER)
    }

    override fun iniView() {
        recyclerView.layoutManager = android.support.v7.widget.GridLayoutManager(context, config.shareStyle.span)
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