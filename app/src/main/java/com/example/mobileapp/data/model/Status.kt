package com.example.mobileapp.data.model

import com.example.mobileapp.data.remote.model.RemoteStatus

enum class Status {
    ON, OFF, OPEN, CLOSE, ACTIVE, INACTIVE, LOCKED, UNLOCKED;

    override fun toString(): String {
        return when (this) {
            Status.ON -> "on"
            Status.OFF -> "off"
            Status.OPEN -> "open"
            Status.CLOSE -> "close"
            Status.ACTIVE -> "active"
            Status.INACTIVE -> "inactive"
            Status.LOCKED -> "locked"
            Status.UNLOCKED -> "unlocked"
        }
    }
}