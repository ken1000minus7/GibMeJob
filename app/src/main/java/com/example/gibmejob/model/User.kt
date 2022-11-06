package com.example.gibmejob.model

data class User(
    val uid: String,
    val email: String,
    val name: String,
    var photoUrl: String? = null
)
