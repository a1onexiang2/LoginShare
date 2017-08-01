package com.neilzheng.loginshare.helper

import com.neilzheng.loginshare.login.AuthEvent
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Neil Zheng on 2017/7/25.
 */
internal open class BaseHelper protected constructor() {

    protected fun formatDate(time: Long): String {
        return SimpleDateFormat("yyyy-MM-dd hh:MM:ss", Locale.CHINA).format(Date(time))
    }

    protected fun postFailureEvent(msg: String = "unknown") {
        EventBus.getDefault().post(AuthEvent.generateFailureEvent(msg))
    }

    protected fun postCancelEvent() {
        EventBus.getDefault().post(AuthEvent.generateCancelEvent())
    }

    protected fun postErrorEvent(msg: String = "unknown") {
        EventBus.getDefault().post(AuthEvent.generateFailureEvent(msg))
    }

    protected fun post(any: Any) {
        EventBus.getDefault().post(any)
    }

}