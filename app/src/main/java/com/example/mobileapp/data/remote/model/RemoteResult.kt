package com.example.mobileapp.data.remote.model

import com.google.gson.annotations.SerializedName

class RemoteResult<T : Any> {
    @SerializedName("result")
    lateinit var result: T
}