package com.solidcapstone.semar.data.remote.response

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("photo_url")
    val photoUrl: String,

    @field:SerializedName("video_duration")
    val videoDuration: String,

    @field:SerializedName("video_id")
    val videoId: String,
)
