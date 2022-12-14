package com.example.gibmejob.model

data class User(
    var uid: String = "",
    var email: String = "",
    var name: String = "",
    var photoUrl: String? = null,
    val type: String = "User",
    val skills: List<String> = listOf(),
    var about: String = ""
)
