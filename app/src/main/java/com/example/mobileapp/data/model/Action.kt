package com.example.mobileapp.data.model

import com.example.mobileapp.data.remote.model.RemoteAction

abstract class Action(
    val device: RoutineDevice,
    val name: String,
    val meta: Any?,
    val params: List<*>?
) {
    abstract fun asRemoteModel(): RemoteAction
}