package com.lez.loginshare.login

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

/**
 * Created by Neil Zheng on 2017/7/25.
 */
data class WeiboAuthResult(var uid: String,
                           var access_token: String,
                           var expires_in: String,
                           var remind_in: String,
                           var refresh_token: String) : Parcelable {

    constructor(json: JSONObject)
            : this(if (json.has("uid")) json.getString("uid") else "",
            if (json.has("access_token")) json.getString("access_token") else "",
            if (json.has("expires_in")) json.getString("expires_in") else "",
            if (json.has("remind_in")) json.getString("remind_in") else "",
            if (json.has("refresh_token")) json.getString("refresh_token") else "")

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.uid)
        dest.writeString(this.access_token)
        dest.writeString(this.expires_in)
        dest.writeString(this.remind_in)
        dest.writeString(this.refresh_token)
    }

    override fun describeContents(): Int = 0

    val CREATOR: Parcelable.Creator<WeiboAuthResult> = object : Parcelable.Creator<WeiboAuthResult> {
        override fun createFromParcel(input: Parcel): WeiboAuthResult =
                WeiboAuthResult(input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readString())

        override fun newArray(size: Int): Array<WeiboAuthResult?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "WeiboAuthResult(uid='$uid', access_token='$access_token', expires_in='$expires_in', remind_in='$remind_in', refresh_token='$refresh_token')"
    }
}