package com.example.mobileapp.data.remote.api.adapters

import com.example.mobileapp.data.remote.model.RemoteRoutine
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class RoutineTypeAdapter : JsonDeserializer<RemoteRoutine?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RemoteRoutine? {
        val gson = Gson()
        val jsonRoutineObject = json.asJsonObject
        return gson.fromJson(jsonRoutineObject, object : TypeToken<RemoteRoutine?>() {}.type)
    }
}