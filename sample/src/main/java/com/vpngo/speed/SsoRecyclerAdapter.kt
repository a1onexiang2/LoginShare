package com.vpngo.speed

import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by Neil Zheng on 2017/6/26.
 */
class SsoRecyclerAdapter(val list: Array<String>) : RecyclerView.Adapter<BaseViewHolder>() {

    var onClickListener: BaseViewHolder.onItemClickListener? = null
        set
    var onLongClickListener: BaseViewHolder.onItemLongClickListener? = null
        set

    override fun onCreateViewHolder(vg: ViewGroup?, position: Int): BaseViewHolder {
        return SsoViewHolder(View.inflate(vg?.context, R.layout.item_main_sso_recyclerview, null))
    }

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        if (holder is SsoViewHolder) {
            holder.button.text = list[position]
            if (null != onClickListener) {
                holder.itemView.setOnClickListener { onClickListener!!.onItemClick(position) }
            }
            if (null != onLongClickListener) {
                holder.itemView.setOnLongClickListener { onLongClickListener!!.onItemLongClick(position) }
            }
        }
    }

    fun getItem(position: Int): String {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SsoViewHolder(view: View) : BaseViewHolder(view) {
        var button= view.findViewById<AppCompatButton>(R.id.button)
    }

}