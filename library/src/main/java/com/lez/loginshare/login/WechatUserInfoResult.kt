package com.lez.loginshare.login

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

/**
 * Created by Neil Zheng on 2017/7/25.
 */
data class WechatUserInfoResult(var openid: String,
                                var nickname: String,
                                var sex: Int,
                                var province: String,
                                var city: String,
                                var country: String,
                                var headimgurl: String,
                                var privilege: List<String>,
                                var unionid: String,
                                var language: String) : Parcelable {

    constructor(json: JSONObject)
            : this(if (json.has("openid")) json.getString("openid") else "",
            if (json.has("nickname")) json.getString("nickname") else "",
            if (json.has("sex")) json.getInt("sex") else 0,
            if (json.has("province")) json.getString("province") else "",
            if (json.has("city")) json.getString("city") else "",
            if (json.has("country")) json.getString("country") else "",
            if (json.has("headimgurl")) json.getString("headimgurl") else "",
            if (json.has("privilege")) {
                val jsonArray = json.getJSONArray("privilege")
                val result = arrayListOf<String>()
                if (jsonArray.length() > 0) {
                    (0..jsonArray.length()).mapTo(result) { jsonArray.getString(it) }
                }
                result
            } else emptyList<String>(),
            if (json.has("unionid")) json.getString("unionid") else "",
            if (json.has("language")) json.getString("language") else "")

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.openid)
        dest.writeString(this.nickname)
        dest.writeInt(this.sex)
        dest.writeString(this.province)
        dest.writeString(this.city)
        dest.writeString(this.country)
        dest.writeString(this.headimgurl)
        dest.writeStringList(this.privilege)
        dest.writeString(this.unionid)
        dest.writeString(this.language)
    }

    override fun describeContents(): Int = 0

    val CREATOR: Parcelable.Creator<WechatUserInfoResult> = object : Parcelable.Creator<WechatUserInfoResult> {
        override fun createFromParcel(input: Parcel): WechatUserInfoResult {
            val openid: String = input.readString()
            val nickname: String = input.readString()
            val sex: Int = input.readInt()
            val province: String = input.readString()
            val city: String = input.readString()
            val country: String = input.readString()
            val headimgurl: String = input.readString()
            val privilege: List<String> = arrayListOf()
            input.readStringList(privilege)
            val unionid: String = input.readString()
            val language: String = input.readString()
            return WechatUserInfoResult(openid,
                    nickname,
                    sex,
                    province,
                    city,
                    country,
                    headimgurl,
                    privilege,
                    unionid,
                    language)
        }

        override fun newArray(size: Int): Array<WechatUserInfoResult?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "WechatUserInfoResult(openid='$openid', nickname='$nickname', sex=$sex, province='$province', city='$city', country='$country', headimgurl='$headimgurl', privilege=$privilege, unionid='$unionid', language='$language')"
    }

}