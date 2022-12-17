package com.example.gibmejob.model

data class JobApplication(
    var applicationId: String = "",
    var userId: String,
    var userName: String,
    var companyId: String,
    var companyName: String,
    var jobDescription: String,
    var selected: Boolean = false
)
