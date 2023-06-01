package com.solidcapstone.semar.data.remote.response

import com.google.gson.annotations.SerializedName

data class TicketResponse(
    @field:SerializedName("data")
    val data: TicketData,

    @field:SerializedName("message")
    val message: String,
)

data class TicketData(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("event_id")
    val eventId: Int,

    @field:SerializedName("method")
    val method: String,

    @field:SerializedName("payment_picture")
    val paymentPictureUrl: String,

    @field:SerializedName("ticket_bought")
    val ticketBought: Int,

    @field:SerializedName("total_count")
    val totalCount: Int,
)


