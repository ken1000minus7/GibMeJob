package com.example.gibmejob.model

data class Company(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    var photoUrl: String? = null,
    val type: String = "Company"
)
