package com.neilzheng.loginshare.login

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

/**
 * Created by Neil Zheng on 2017/7/25.
 */
data class QqAuthResult(var ret: Int,
                        var openid: String,
                        var access_token: String,
                        var pay_token: String,
                        var expires_in: Long,
                        var pf: String,
                        var pfkey: String,
                        var msg: String,
                        var login_cost: Long,
                        var query_authority_cost: Long,
                        var authority_cost: Long) : Parcelable {

    constructor(json: JSONObject)
            : this(if (json.has("ret")) json.getInt("ret") else 0,
            if (json.has("openid")) json.getString("openid") else "",
            if (json.has("access_token")) json.getString("access_token") else "",
            if (json.has("pay_token")) json.getString("pay_token") else "",
            if (json.has("expires_in")) json.getLong("expires_in") else 0,
            if (json.has("pf")) json.getString("pf") else "",
            if (json.has("pfkey")) json.getString("pfkey") else "",
            if (json.has("msg")) json.getString("msg") else "",
            if (json.has("login_cost")) json.getLong("login_cost") else 0,
            if (json.has("query_authority_cost")) json.getLong("query_authority_cost") else 0,
            if (json.has("authority_cost")) json.getLong("authority_cost") else 0)

    override fun writeToParcel(dest: android.os.Parcel, flags: Int) {
        dest.writeInt(this.ret)
        dest.writeString(this.openid)
        dest.writeString(this.access_token)
        dest.writeString(this.pay_token)
        dest.writeLong(this.expires_in)
        dest.writeString(this.pf)
        dest.writeString(this.pfkey)
        dest.writeString(this.msg)
        dest.writeLong(this.login_cost)
        dest.writeLong(this.query_authority_cost)
        dest.writeLong(this.authority_cost)
    }

    override fun describeContents(): Int = 0

    val CREATOR: Parcelable.Creator<QqAuthResult> = object : Parcelable.Creator<QqAuthResult> {
        override fun createFromParcel(input: Parcel): QqAuthResult =
                QqAuthResult(input.readInt(),
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readLong(),
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readLong(),
                        input.readLong(),
                        input.readLong())

        override fun newArray(size: Int): Array<QqAuthResult?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "QqAuthResult(ret=$ret, openid='$openid', access_token='$access_token', pay_token='$pay_token', expires_in=$expires_in, pf='$pf', pfkey='$pfkey', msg='$msg', login_cost=$login_cost, query_authority_cost=$query_authority_cost, authority_cost=$authority_cost)"
    }
}