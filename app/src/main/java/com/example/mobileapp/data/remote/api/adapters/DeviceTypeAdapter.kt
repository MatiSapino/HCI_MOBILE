package com.example.mobileapp.data.remote.api.adapters

import com.example.mobileapp.data.remote.model.RemoteDevice
import com.example.mobileapp.data.remote.model.RemoteDeviceType
import com.example.mobileapp.data.remote.model.remote_devices.RemoteAc
import com.example.mobileapp.data.remote.model.remote_devices.RemoteDoor
import com.example.mobileapp.data.remote.model.remote_devices.RemoteLamp
import com.example.mobileapp.data.remote.model.remote_devices.RemoteTap
import com.example.mobileapp.data.remote.model.remote_devices.RemoteVacuum
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DeviceTypeAdapter : JsonDeserializer<RemoteDevice<*>?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RemoteDevice<*>? {
        val gson = Gson()
        val jsonDeviceObject = json.asJsonObject
        val jsonDeviceTypeObject = jsonDeviceObject["type"].asJsonObject
        val deviceTypeId = jsonDeviceTypeObject["id"].asString
        return when (deviceTypeId) {
            RemoteDeviceType.LAMP_TYPE_ID -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteLamp?>() {}.type)
            RemoteDeviceType.TAP_TYPE_ID -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteTap?>() {}.type)
            RemoteDeviceType.AC_TYPE_ID -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteAc?>() {}.type)
            RemoteDeviceType.DOOR_TYPE_ID -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteDoor?>() {}.type)
            RemoteDeviceType.VACUUM_TYPE_ID -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteVacuum?>() {}.type)
            else -> null
        }

    }
}