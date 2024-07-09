package com.example.mobileapp.data.model

enum class Status {
    ON, OFF, OPENED, CLOSED, ACTIVE, INACTIVE, LOCKED, UNLOCKED;

    override fun toString(): String {
        return when (this) {
            Status.ON -> "on"
            Status.OFF -> "off"
            Status.OPENED -> "opened"
            Status.CLOSED -> "closed"
            Status.ACTIVE -> "active"
            Status.INACTIVE -> "inactive"
            Status.LOCKED -> "locked"
            Status.UNLOCKED -> "unlocked"
        }
    }

    companion object {
        fun stringToStatus(string: String): Status {
            return when (string) {
                "on" -> Status.ON
                "off" -> Status.OFF
                "opened" -> Status.OPENED
                "closed" -> Status.CLOSED
                "active" -> Status.ACTIVE
                "inactive" -> Status.INACTIVE
                "locked" -> Status.LOCKED
                "unlocked" -> Status.UNLOCKED
                else -> throw IllegalArgumentException("Unknown status: $string")
            }
        }
    }
}