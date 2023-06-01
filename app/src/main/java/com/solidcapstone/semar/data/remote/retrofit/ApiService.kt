package com.solidcapstone.semar.data.remote.retrofit

import com.solidcapstone.semar.data.remote.response.EventResponse
import com.solidcapstone.semar.data.remote.response.PredictResponse
import com.solidcapstone.semar.data.remote.response.TicketResponse
import com.solidcapstone.semar.data.remote.response.VideoResponse
import com.solidcapstone.semar.data.remote.response.WayangResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("https://wayang-prediction-mrlpwmp4cq-et.a.run.app/predict")
    suspend fun predictWayang(
        @Part file: MultipartBody.Part,
    ): PredictResponse

    @POST("wayanglist")
    suspend fun getListWayang(): List<WayangResponse>

    @Multipart
    @POST("wayang")
    suspend fun getWayang(
        @Part("field_id") id: RequestBody,
    ): WayangResponse

    @POST("videolist")
    suspend fun getListVideo(): List<VideoResponse>

    @Multipart
    @POST("video")
    suspend fun getVideo(
        @Part("field_id") id : RequestBody,
    ): VideoResponse

    @POST("eventlist")
    suspend fun getListEvent(): List<EventResponse>

    @Multipart
    @POST("event")
    suspend fun getEvent(
        @Part("field_id") id : RequestBody,
    ): EventResponse

    @Multipart
    @POST("ticket_event/{event_id}")
    suspend fun uploadTicketEvent(
        @Part("event_id") eventId : RequestBody,
        @Part("tickets_bought") ticketsBought: RequestBody,
        @Part("name") name : RequestBody,
        @Part("email") email : RequestBody,
        @Part("payment_method") paymentMethod : RequestBody,
        @Part("file") file: MultipartBody.Part,
    ) : TicketResponse

}