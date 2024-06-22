package com.example.mobileapp.data.model

import com.example.mobileapp.data.remote.model.RemoteRoutine

abstract class Routine(
    val id: String?,
    val name: String,
    val actions: List<Action>,
    val meta: Any?
) {
    abstract fun asRemoteModel(): RemoteRoutine
}