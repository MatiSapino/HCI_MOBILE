package com.example.mobileapp.data.remote.api

import com.example.mobileapp.data.remote.model.RemoteResult
import com.example.mobileapp.data.remote.model.RemoteRoom
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RoomService {

    @GET("rooms")
    suspend fun getRooms(): Response<RemoteResult<List<RemoteRoom>>>

    @POST("rooms")
    suspend fun addRoom(@Body room: RemoteRoom): Response<RemoteResult<RemoteRoom>>

    @GET("rooms/{roomId}")
    suspend fun getRoom(@Path("roomId") roomId: String): Response<RemoteResult<RemoteRoom>>

    @PUT("rooms/{roomId}")
    suspend fun modifyRoom(
        @Path("roomId") roomId: String,
        @Body room: RemoteRoom
    ): Response<RemoteResult<Boolean>>

    @DELETE("rooms/{roomId}")
    suspend fun deleteRoom(@Path("roomId") roomId: String): Response<RemoteResult<Boolean>>
}