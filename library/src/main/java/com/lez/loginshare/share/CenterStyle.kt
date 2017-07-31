package com.lez.loginshare.share

/**
 * Created by Neil Zheng on 2017/7/27.
 */
class CenterStyle(override var corner: Boolean = true, override var span: Int = 4) : BaseStyle(corner, span) {

    constructor(corner: Boolean) : this(corner, 4)
    constructor() : this(true)

    override fun TAG(): String = "Center"

    override fun getDialog(config: ShareConfig): CenterShareDialog
            = BaseShareDialog.wrap(config, CenterShareDialog::class.java)

}