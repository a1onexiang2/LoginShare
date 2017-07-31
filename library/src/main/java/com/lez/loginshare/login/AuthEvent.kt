package com.lez.loginshare.login

/**
 * Created by Neil Zheng on 2017/7/25.
 */
class AuthEvent<out T>(val code: Int, val data: T? = null, val error: Throwable = Throwable()) {

    constructor(data: T?) : this(CODE_SUCCESS, data = data)

    constructor(error: Throwable) : this(CODE_FAILURE, error = error)

    internal companion object {
        val CODE_CANCEL = 0
        val CODE_SUCCESS = -1
        val CODE_FAILURE = -2
        val CODE_ERROR = -3

        fun generateFailureEvent(msg: String = "unknown"): AuthEvent<Void> = AuthEvent(Throwable(msg))

        fun generateCancelEvent(): AuthEvent<Void> = AuthEvent(CODE_CANCEL)

        fun generateErrorEvent(msg: String = "unknown"): AuthEvent<Void> = AuthEvent(CODE_ERROR, null, Throwable(msg))
    }

    fun isSuccess(): Boolean = code == CODE_SUCCESS

    fun isFailure(): Boolean = code == CODE_FAILURE

    fun isError(): Boolean = code == CODE_ERROR

    fun isCancel(): Boolean = code == CODE_CANCEL

    override fun toString(): String {
        return "AuthEvent(code=$code, data=$data, error=$error)"
    }

}