package com.example.gibmejob.model

data class JobApplication(
    var applicationId: String = "",
    var userId: String = "",
    var userName: String = "",
    var job : String = "",
    var jobId: String = "",
    var email: String = "",
    var phone: String = "",
    var skills: List<String> = listOf(),
    var companyId: String = "",
    var companyName: String = "",
    var selected: Boolean = false
)
