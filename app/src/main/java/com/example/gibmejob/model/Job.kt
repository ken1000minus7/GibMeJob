package com.example.gibmejob.model

data class Job(
    var jobId: String = "",
    var title: String,
    var description: String,
    var companyUid: String,
    var companyName: String
)
