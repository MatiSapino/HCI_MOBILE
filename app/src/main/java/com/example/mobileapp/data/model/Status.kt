package com.example.mobileapp.data.model

import com.example.mobileapp.data.remote.model.RemoteStatus

enum class Status {
    ON, OFF;

    companion object {
        fun asRemoteModel(value: Status): String {
            return when (value) {
                ON -> RemoteStatus.ON
                else -> RemoteStatus.OFF
            }
        }
    }
}