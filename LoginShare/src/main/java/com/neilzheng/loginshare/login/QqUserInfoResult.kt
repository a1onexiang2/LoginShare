package com.neilzheng.loginshare.login

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

/**
 * Created by Neil Zheng on 2017/7/25.
 */
data class QqUserInfoResult(var is_yellow_year_vip: String,
                            var ret: Int,
                            var figureurl_qq_1: String,
                            var figureurl_qq_2: String,
                            var nickname: String,
                            var yellow_vip_level: String,
                            var is_lost: Int,
                            var msg: String,
                            var city: String,
                            var figureurl_1: String,
                            var vip: String,
                            var level: String,
                            var figureurl_2: String,
                            var province: String,
                            var gender: String,
                            var is_yellow_vip: String,
                            var figureurl: String,
                            var open_id: String): Parcelable {

    constructor(json: JSONObject)
            : this(if (json.has("is_yellow_year_vip")) json.getString("is_yellow_year_vip") else "",
            if (json.has("ret")) json.getInt("ret") else 0,
            if (json.has("figureurl_qq_1")) json.getString("figureurl_qq_1") else "",
            if (json.has("figureurl_qq_2")) json.getString("figureurl_qq_2") else "",
            if (json.has("nickname")) json.getString("nickname") else "",
            if (json.has("yellow_vip_level")) json.getString("yellow_vip_level") else "",
            if (json.has("is_lost")) json.getInt("is_lost") else 0,
            if (json.has("msg")) json.getString("msg") else "",
            if (json.has("city")) json.getString("city") else "",
            if (json.has("figureurl_1")) json.getString("figureurl_1") else "",
            if (json.has("vip")) json.getString("vip") else "",
            if (json.has("level")) json.getString("level") else "",
            if (json.has("figureurl_2")) json.getString("figureurl_2") else "",
            if (json.has("province")) json.getString("province") else "",
            if (json.has("gender")) json.getString("gender") else "",
            if (json.has("is_yellow_vip")) json.getString("is_yellow_vip") else "",
            if (json.has("figureurl")) json.getString("figureurl") else "",
            if (json.has("open_id")) json.getString("open_id") else "")

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.is_yellow_year_vip)
        dest.writeInt(this.ret)
        dest.writeString(this.figureurl_qq_1)
        dest.writeString(this.figureurl_qq_2)
        dest.writeString(this.nickname)
        dest.writeString(this.yellow_vip_level)
        dest.writeInt(this.is_lost)
        dest.writeString(this.msg)
        dest.writeString(this.city)
        dest.writeString(this.figureurl_1)
        dest.writeString(this.vip)
        dest.writeString(this.level)
        dest.writeString(this.figureurl_2)
        dest.writeString(this.province)
        dest.writeString(this.gender)
        dest.writeString(this.is_yellow_vip)
        dest.writeString(this.figureurl)
        dest.writeString(this.open_id)
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
        return "QqUserInfoResult(is_yellow_year_vip='$is_yellow_year_vip', ret=$ret, figureurl_qq_1='$figureurl_qq_1', figureurl_qq_2='$figureurl_qq_2', nickname='$nickname', yellow_vip_level='$yellow_vip_level', is_lost=$is_lost, msg='$msg', city='$city', figureurl_1='$figureurl_1', vip='$vip', level='$level', figureurl_2='$figureurl_2', province='$province', gender='$gender', is_yellow_vip='$is_yellow_vip', figureurl='$figureurl', open_id='$open_id')"
    }

}