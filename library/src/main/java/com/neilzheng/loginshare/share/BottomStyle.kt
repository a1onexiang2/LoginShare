package com.neilzheng.loginshare.share

/**
 * Created by Neil Zheng on 2017/7/27.
 */
class BottomStyle(override var corner: Boolean = true, override var span: Int = 4) : BaseStyle(corner) {

    constructor(corner: Boolean) : this(corner, 4)
    constructor() : this(true)

    override fun TAG(): String = "Bottom"

    override fun getDialog(config: ShareConfig): BottomShareDialog
            = BaseShareDialog.wrap(config, BottomShareDialog::class.java)

}