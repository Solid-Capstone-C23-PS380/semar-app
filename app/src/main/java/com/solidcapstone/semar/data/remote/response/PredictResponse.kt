package com.solidcapstone.semar.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("result")
    val result: String,

    @field:SerializedName("score")
    val score : Float,
)