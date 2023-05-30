package com.solidcapstone.semar.data.remote.response

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("photo_url")
    val photoUrl: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("price")
    val price: Int,

    @field:SerializedName("time")
    val time: String
)