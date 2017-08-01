package com.neilzheng.loginshare.login

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

/**
 * Created by Neil Zheng on 2017/7/25.
 */
data class WeiboUserInfoResult(var id: Long,
                               var name: String,
                               var screen_name: String,
                               var profile_image_url: String,
                               var avatar_large: String,
                               var description: String,
                               var location: String,
                               var url: String,
                               var verified_source_url: String,
                               var block_app: Int,
                               var remark: String,
                               var verified_type: Int,
                               var verified_reason: String,
                               var statuses_count: Int,
                               var lang: String,
                               var verified_source: String,
                               var credit_score: Int,
                               var city: String,
                               var verified_trade: String,
                               var following: Boolean,
                               var favourites_count: Int,
                               var idstr: String,
                               var verified: Boolean,
                               var domain: String,
                               var province: String,
                               var gender: String,
                               var created_at: String,
                               var user_ability: Int,
                               var weihao: String,
                               var followers_count: Int,
                               var online_status: Int,
                               var profile_url: String,
                               var bi_followers_count: Int,
                               var geo_enabled: Boolean,
                               var star: Int,
                               var urank: Int) : Parcelable {

    constructor(json: JSONObject)
            : this(if (json.has("id")) json.getLong("id") else 0,
            if (json.has("name")) json.getString("name") else "",
            if (json.has("screen_name")) json.getString("screen_name") else "",
            if (json.has("profile_image_url")) json.getString("profile_image_url") else "",
            if (json.has("avatar_large")) json.getString("avatar_large") else "",
            if (json.has("description")) json.getString("description") else "",
            if (json.has("location")) json.getString("location") else "",
            if (json.has("imageUrlList")) json.getString("imageUrlList") else "",
            if (json.has("verified_source_url")) json.getString("verified_source_url") else "",
            if (json.has("block_app")) json.getInt("block_app") else 0,
            if (json.has("remark")) json.getString("remark") else "",
            if (json.has("verified_type")) json.getInt("verified_type") else 0,
            if (json.has("verified_reason")) json.getString("verified_reason") else "",
            if (json.has("statuses_count")) json.getInt("statuses_count") else 0,
            if (json.has("lang")) json.getString("lang") else "",
            if (json.has("verified_source")) json.getString("verified_source") else "",
            if (json.has("credit_score")) json.getInt("credit_score") else 0,
            if (json.has("city")) json.getString("city") else "",
            if (json.has("verified_trade")) json.getString("verified_trade") else "",
            if (json.has("following")) json.getBoolean("following") else false,
            if (json.has("favourites_count")) json.getInt("favourites_count") else 0,
            if (json.has("idstr")) json.getString("idstr") else "",
            if (json.has("verified")) json.getBoolean("verified") else false,
            if (json.has("domain")) json.getString("domain") else "",
            if (json.has("province")) json.getString("province") else "",
            if (json.has("gender")) json.getString("gender") else "",
            if (json.has("created_at")) json.getString("created_at") else "",
            if (json.has("user_ability")) json.getInt("user_ability") else 0,
            if (json.has("weihao")) json.getString("weihao") else "",
            if (json.has("followers_count")) json.getInt("followers_count") else 0,
            if (json.has("online_status")) json.getInt("online_status") else 0,
            if (json.has("profile_url")) json.getString("profile_url") else "",
            if (json.has("bi_followers_count")) json.getInt("bi_followers_count") else 0,
            if (json.has("geo_enabled")) json.getBoolean("geo_enabled") else false,
            if (json.has("star")) json.getInt("star") else 0,
            if (json.has("urank")) json.getInt("urank") else 0)

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(this.id)
        dest.writeString(this.name)
        dest.writeString(this.screen_name)
        dest.writeString(this.profile_image_url)
        dest.writeString(this.avatar_large)
        dest.writeString(this.description)
        dest.writeString(this.location)
        dest.writeString(this.url)
        dest.writeString(this.verified_source_url)
        dest.writeInt(this.block_app)
        dest.writeString(this.remark)
        dest.writeInt(this.verified_type)
        dest.writeString(this.verified_reason)
        dest.writeInt(this.statuses_count)
        dest.writeString(this.lang)
        dest.writeString(this.verified_source)
        dest.writeInt(this.credit_score)
        dest.writeString(this.city)
        dest.writeString(this.verified_trade)
        dest.writeInt(if(this.following) 1 else 0)
        dest.writeInt(this.favourites_count)
        dest.writeString(this.idstr)
        dest.writeInt(if(this.following) 1 else 0)
        dest.writeString(this.domain)
        dest.writeString(this.province)
        dest.writeString(this.gender)
        dest.writeString(this.created_at)
        dest.writeInt(this.user_ability)
        dest.writeString(this.weihao)
        dest.writeInt(this.followers_count)
        dest.writeInt(this.online_status)
        dest.writeString(this.profile_url)
        dest.writeInt(this.bi_followers_count)
        dest.writeInt(if(this.following) 1 else 0)
        dest.writeInt(this.star)
        dest.writeInt(this.urank)
    }

    override fun describeContents(): Int = 0

    val CREATOR: Parcelable.Creator<WeiboUserInfoResult> = object : Parcelable.Creator<WeiboUserInfoResult> {
        override fun createFromParcel(input: Parcel): WeiboUserInfoResult =
                WeiboUserInfoResult(input.readLong(),
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readInt(),
                        input.readString(),
                        input.readInt(),
                        input.readString(),
                        input.readInt(),
                        input.readString(),
                        input.readString(),
                        input.readInt(),
                        input.readString(),
                        input.readString(),
                        input.readInt() == 1,
                        input.readInt(),
                        input.readString(),
                        input.readInt() == 1,
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readString(),
                        input.readInt(),
                        input.readString(),
                        input.readInt(),
                        input.readInt(),
                        input.readString(),
                        input.readInt(),
                        input.readInt() == 1,
                        input.readInt(),
                        input.readInt())

        override fun newArray(size: Int): Array<WeiboUserInfoResult?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "WeiboUserInfoResult(id=$id, name='$name', screen_name='$screen_name', profile_image_url='$profile_image_url', avatar_large='$avatar_large', description='$description', location='$location', imageUrlList='$url', verified_source_url='$verified_source_url', block_app=$block_app, remark='$remark', verified_type=$verified_type, verified_reason='$verified_reason', statuses_count=$statuses_count, lang='$lang', verified_source='$verified_source', credit_score=$credit_score, city='$city', verified_trade='$verified_trade', following=$following, favourites_count=$favourites_count, idstr='$idstr', verified=$verified, domain='$domain', province='$province', gender='$gender', created_at='$created_at', user_ability=$user_ability, weihao='$weihao', followers_count=$followers_count, online_status=$online_status, profile_url='$profile_url', bi_followers_count=$bi_followers_count, geo_enabled=$geo_enabled, star=$star, urank=$urank)"
    }
}