package com.neilzheng.loginshare.share

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.view.ViewCompat
import android.view.Gravity
import android.view.ViewGroup
import com.neilzheng.loginshare.R
import kotlinx.android.synthetic.main.dialog_share_dropdown.*

/**
 * Created by Neil Zheng on 2017/7/26.
 */
class DropdownShareDialog : BaseShareDialog() {

    override fun styleResId(): Int = R.style.ShareDialog_Dropdown

    override fun themeResId(): Int = R.style.ShareDialog_Dropdown

    override fun layoutResId(): Int {
        return if (config.shareStyle.corner) R.layout.dialog_share_dropdown_corner else R.layout.dialog_share_dropdown
    }

    override fun initDialog() {
        dialog.window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setGravity(Gravity.LEFT or Gravity.TOP)
        val anchor = (config.shareStyle as DropdownStyle).anchor
        val positions = IntArray(2)
        anchor.getLocationOnScreen(positions)
        dialog.window.attributes.x = positions[0]
        dialog.window.attributes.y = positions[1] +
                if ((config.shareStyle as DropdownStyle).isFullscreen) 0
                else dialog.context.resources.getDimensionPixelOffset(R.dimen.height_statusBar)
        if (config.shareStyle.corner) {
            dialog.window.setBackgroundDrawable(dialog.context.resources.getDrawable(R.drawable.bg_dialog_share_dropdown))
        } else {
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        }
    }

    override fun iniView() {
        recyclerView.layoutManager = android.support.v7.widget.LinearLayoutManager(context)
        val adapter = ShareRecyclerAdapter(config.platformList, false)
        recyclerView.adapter = adapter
        adapter.onClickListener = object : BaseViewHolder.onItemClickListener {
            override fun onItemClick(position: Int) {
                doShare(config.shareContent, adapter.getItem(position))
                dismiss()
            }
        }
        ViewCompat.setElevation(recyclerView, 5f)
    }
}