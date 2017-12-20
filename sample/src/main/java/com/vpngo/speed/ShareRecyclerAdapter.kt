package com.vpngo.speed

import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import com.neilzheng.loginshare.share.SharePlatform

/**
 * Created by Neil Zheng on 2017/6/26.
 */
class ShareRecyclerAdapter(val list: Array<SharePlatform>) : RecyclerView.Adapter<BaseViewHolder>() {

    private var selected: SparseBooleanArray = SparseBooleanArray()

    override fun onCreateViewHolder(vg: ViewGroup?, position: Int): BaseViewHolder {
        return ShareViewHolder(View.inflate(vg?.context, R.layout.item_main_share_recyclerview, null))
    }

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        if (holder is ShareViewHolder) {
            holder.checkbox.text = list[position].name
            holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
                selected.put(position, isChecked)
            }
        }
    }

    fun isSelected(position: Int): Boolean = selected[position]

    fun getItem(position: Int): SharePlatform {
        return list[position]
    }

    fun getSelectedPlatforms(): Array<SharePlatform> {
        val result: ArrayList<SharePlatform> = arrayListOf()
        (0..(itemCount - 2))
                .filter { isSelected(it) }
                .forEach { result.add(getItem(it)) }
        return result.toTypedArray()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ShareViewHolder(view: View) : BaseViewHolder(view) {
        var checkbox = view.findViewById<AppCompatCheckBox>(R.id.checkbox)
    }

}