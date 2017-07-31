package com.neilzheng.loginshare.share

/**
 * Created by Neil Zheng on 2017/7/27.
 */
class SharePlatform(val type: Platforms,
                    val name: String,
                    val icon: Int = 0) {

    constructor(type: Platforms, name: String) : this(type, name, 0)

}
