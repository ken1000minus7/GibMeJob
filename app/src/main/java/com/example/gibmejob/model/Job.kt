package com.example.gibmejob.model

import java.io.Serializable

data class Job(
    var jobId: String = "",
    var title: String = "",
    var location: String = "",
    var jobType: String = "",
    var description: String = "",
    var companyUid: String = "",
    var companyName: String = "",
    var skillsRequired : List<String> = listOf(),
    var totalApplicants : Int = 0,
    var status : String = "Open"
) : Serializable
