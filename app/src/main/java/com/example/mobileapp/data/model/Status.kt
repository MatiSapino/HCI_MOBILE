package com.example.mobileapp.data.model

import com.example.mobileapp.data.remote.model.RemoteStatus

enum class Status {
    ON, OFF, OPENED, CLOSED, ACTIVE, INACTIVE, LOCKED, UNLOCKED;

    override fun toString(): String {
        return when (this) {
            Status.ON -> "on"
            Status.OFF -> "off"
            Status.OPENED -> "open"
            Status.CLOSED -> "close"
            Status.ACTIVE -> "active"
            Status.INACTIVE -> "inactive"
            Status.LOCKED -> "locked"
            Status.UNLOCKED -> "unlocked"
        }
    }
}