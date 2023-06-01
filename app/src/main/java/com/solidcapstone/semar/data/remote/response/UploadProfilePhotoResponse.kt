package com.solidcapstone.semar.data.remote.response

import com.google.gson.annotations.SerializedName

data class UploadProfilePhotoResponse(
    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)
