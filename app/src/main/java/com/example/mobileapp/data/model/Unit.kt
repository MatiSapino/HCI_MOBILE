package com.example.mobileapp.data.model

enum class Unit {
    Ml, Cl, Dl, L;
    override fun toString(): String {
        return when (this) {
            Ml -> "Ml"
            Cl -> "Cl"
            Dl -> "Dl"
            L -> "L"
        }
    }
}