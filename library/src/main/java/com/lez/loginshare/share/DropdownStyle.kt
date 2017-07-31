package com.lez.loginshare.share

import android.view.View

/**
 * Created by Neil Zheng on 2017/7/27.
 */
class DropdownStyle(override var corner: Boolean = true, var anchor: View,
                    var isFullscreen: Boolean = false) : BaseStyle(corner) {

    constructor(anchor: View, isFullscreen: Boolean) : this(true, anchor, isFullscreen)
    constructor(anchor: View) : this(anchor, false)

    override fun TAG(): String = "Dropdown"

    override fun getDialog(config: ShareConfig): DropdownShareDialog
            = BaseShareDialog.wrap(config, DropdownShareDialog::class.java)

}