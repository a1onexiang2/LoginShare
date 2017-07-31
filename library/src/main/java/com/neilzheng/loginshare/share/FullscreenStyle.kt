package com.neilzheng.loginshare.share

/**
 * Created by Neil Zheng on 2017/7/27.
 */
class FullscreenStyle(override var corner: Boolean = true, override var span: Int = 4) : BaseStyle(corner, span) {

    constructor(corner: Boolean) : this(corner, 4)
    constructor() : this(true)

    override fun TAG(): String = "Fullscreen"

    override fun getDialog(config: ShareConfig): FullscreenShareDialog =
            BaseShareDialog.wrap(config, FullscreenShareDialog::class.java)

}