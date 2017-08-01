package com.neilzheng.loginshare.share

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.neilzheng.loginshare.R

/**
 * Created by Neil Zheng on 2017/7/26.
 */
class ShareRecyclerAdapter(val list: ArrayList<SharePlatform>, val showIcon: Boolean = true) : RecyclerView.Adapter<BaseViewHolder>() {

    constructor(list: ArrayList<SharePlatform>) : this(list, true)

    var onClickListener: BaseViewHolder.onItemClickListener? = null
        set
    var onLongClickListener: BaseViewHolder.onItemLongClickListener? = null
        set

    override fun onCreateViewHolder(vg: ViewGroup?, position: Int): BaseViewHolder {
        return ShareRecyclerAdapter.SsoViewHolder(View.inflate(vg?.context, R.layout.item_share, null))
    }

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        if (holder is ShareRecyclerAdapter.SsoViewHolder) {
            holder.text.text = list[position].name
            if (showIcon) {
                val drawable = holder.itemView.context.resources.getDrawable(getDefaultIcon(list[position]))
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                holder.text.setCompoundDrawables(null, drawable, null, null)
            }
            if (null != onClickListener) {
                holder.itemView.setOnClickListener { onClickListener!!.onItemClick(position) }
            }
            if (null != onLongClickListener) {
                holder.itemView.setOnLongClickListener { onLongClickListener!!.onItemLongClick(position) }
            }
        }
    }

    fun getDefaultIcon(platform: SharePlatform): Int {
        if (platform.icon == 0) {
            return when (platform.type) {
                Platforms.QQ -> R.drawable.icon_share_qq
                Platforms.QZONE -> R.drawable.icon_share_qzone
                Platforms.WECHAT -> R.drawable.icon_share_wechat
                Platforms.MOMENTS -> R.drawable.icon_share_moments
                Platforms.WEIBO -> R.drawable.icon_share_sina_weibo
                Platforms.COPY -> R.drawable.icon_share_copy
                Platforms.SYSTEM -> R.drawable.icon_share_system
            }
        }
        return platform.icon
    }

    fun getItem(position: Int): SharePlatform {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SsoViewHolder(view: View) : BaseViewHolder(view) {
        var text = view.findViewById(R.id.text) as AppCompatTextView
    }

}