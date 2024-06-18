package com.example.mobileapp

class DataSourceException(
    var code: Int,
    message: String,
    var details: List<String>?
) : Exception(message)