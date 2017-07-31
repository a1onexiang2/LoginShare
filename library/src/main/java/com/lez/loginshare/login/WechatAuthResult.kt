package com.lez.loginshare.login

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

/**
 * Created by Neil Zheng on 2017/7/25.
 */
data class WechatAuthResult(var access_token: String,
                            var expires_in: Long,
                            var refresh_token: String,
                            var openid: String,
                            var scope: String,
                            var unionid: String,
                            var errcode: Int,
                            var errmsg: String) : Parcelable {

    constructor(json: JSONObject)
            : this(if (json.has("access_token")) json.getString("access_token") else "",
            if (json.has("expires_in")) json.getLong("expires_in") else 0,
            if (json.has("refresh_token")) json.getString("refresh_token") else "",
            if (json.has("openid")) json.getString("openid") else "",
            if (json.has("scope")) json.getString("scope") else "",
            if (json.has("unionid")) json.getString("unionid") else "",
            if (json.has("errcode")) json.getInt("errcode") else 0,
            if (json.has("errmsg")) json.getString("errmsg") else "")

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.access_token)
        dest.writeLong(this.expires_in)
        dest.writeString(this.refresh_token)
        dest.writeString(this.openid)
        dest.writeString(this.scope)
        dest.writeString(this.unionid)
        dest.writeInt(this.errcode)
        dest.writeString(this.errmsg)
    }

    override fun describeContents(): Int = 0

    val CREATOR: Parcelable.Creator<WechatAuthResult> = object : Parcelable.Creator<WechatAuthResult> {
        override fun createFromParcel(input: Parcel): WechatAuthResult =
                WechatAuthResult(input.readString(),
                        input.readLong(),
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readInt(),
                        input.readString())

        override fun newArray(size: Int): Array<WechatAuthResult?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "WechatAuthResult(access_token='$access_token', expires_in=$expires_in, refresh_token='$refresh_token', openid='$openid', scope='$scope', unionid='$unionid', errcode=$errcode, errmsg='$errmsg')"
    }

}