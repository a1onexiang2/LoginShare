package com.lez.loginshare.share

/**
 * Created by Neil Zheng on 2017/7/27.
 */
abstract class BaseStyle(open var corner: Boolean = true, open var span: Int = 4) {

    constructor(corner: Boolean) : this(corner, 4)

    abstract fun TAG(): String

    abstract fun getDialog(config: ShareConfig): BaseShareDialog

}