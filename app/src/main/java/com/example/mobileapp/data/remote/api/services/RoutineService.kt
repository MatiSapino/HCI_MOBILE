package com.example.mobileapp.data.remote.api.services;

import com.example.mobileapp.data.remote.model.RemoteResult
import com.example.mobileapp.data.remote.model.RemoteRoutine
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

public interface RoutineService {
    @GET("routines")
    suspend fun getRoutines(): Response<RemoteResult<List<RemoteRoutine<*>>>>

    @POST("routines")
    suspend fun addRoutine(@Body routine: RemoteRoutine<*>): Response<RemoteResult<RemoteRoutine<*>>>

    @GET("routines/{routineId}")
    suspend fun getRoutine(@Path("routineId") routineId: String): Response<RemoteResult<RemoteRoutine<*>>>

    @PUT("routines/{routineId}/execute")
    suspend fun executeRoutine(
        @Path("routineId") routineId: String,
    ): Response<RemoteResult<Array<*>>>

    @DELETE("routines/{routineId}")
    suspend fun deleteRoutine(@Path("routineId") routineId: String): Response<RemoteResult<Boolean>>

}
