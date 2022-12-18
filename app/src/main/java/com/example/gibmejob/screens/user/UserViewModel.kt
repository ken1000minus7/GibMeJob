package com.example.gibmejob.screens.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gibmejob.model.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.log10
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

class UserViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    val user: MutableLiveData<User?> = MutableLiveData(null)
    val company: MutableLiveData<Company?> = MutableLiveData(null)
    val jobs: MutableStateFlow<MutableList<Job>> = MutableStateFlow(mutableListOf())
    val jobApplications: MutableLiveData<MutableList<JobApplication>> =
        MutableLiveData(mutableListOf())
    val jobRecommendations: MutableLiveData<MutableList<Job>> = MutableLiveData(mutableListOf())
    val userRecommendations: MutableLiveData<MutableList<User>> = MutableLiveData(mutableListOf())
    val job: MutableStateFlow<Job?> = MutableStateFlow(null)

    //Search functionalities
    var isSearching = mutableStateOf(false)
    var isSearchStarted = true
    var cachedJobResults = listOf<Job>()

    val uid
        get() = auth.uid!!
    val name
        get() = user.value?.name ?: ""


    fun getUser() {
        viewModelScope.launch {
            db.collection("Users")
                .document(auth.uid!!)
                .addSnapshotListener { value, error ->
                    if (value?.exists() == true) {
                        user.postValue(value.toObject(User::class.java))
                        getJobRecommendations()
                    } else {
                        error?.printStackTrace()
                    }
                }
        }
    }

    fun getCompany() {
        viewModelScope.launch {
            db.collection(Constants.Users)
                .document(auth.uid!!)
                .addSnapshotListener { value, error ->
                    if (value?.exists() == true) {
                        company.postValue(value.toObject(Company::class.java))
                    } else {
                        error?.printStackTrace()
                    }
                }
        }
    }

    fun addJob(job: Job, onComplete: () -> Unit) {
        viewModelScope.launch {
            db.collection(Constants.Jobs)
                .add(job)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        job.jobId = it.result.id
                        db.collection(Constants.Jobs)
                            .document(job.jobId)
                            .set(job, SetOptions.merge())
                    }
                    onComplete()
                }
        }
    }

    fun updateJobApplicantsCount(jobId: String) {
        viewModelScope.launch {
            db.collection(Constants.Jobs)
                .document(jobId)
                .update("totalApplicants", FieldValue.increment(1))
                .addOnSuccessListener { }
                .addOnFailureListener { }
        }
    }

    fun addApplication(jobApplication: JobApplication, onComplete: () -> Unit) {
        viewModelScope.launch {
            db.collection(Constants.JobApplications)
                .add(jobApplication)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        jobApplication.applicationId = it.result.id
                        db.collection(Constants.JobApplications)
                            .document(jobApplication.applicationId)
                            .set(jobApplication, SetOptions.merge())
                    }
                    onComplete()
                }
        }
    }

    fun getAllJobs() {
        viewModelScope.launch {
            db.collection(Constants.Jobs)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        jobs.value = (it.result.toObjects(Job::class.java))
                    } else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }

    fun getCompanyJobs(companyId: String) {
        viewModelScope.launch {
            db.collection(Constants.Jobs)
                .whereEqualTo("companyUid", companyId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        jobs.value = (it.result.toObjects(Job::class.java))
                    } else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }

    fun getUserJobApplications(userId: String) {
        viewModelScope.launch {
            db.collection(Constants.JobApplications)
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        jobApplications.postValue(it.result.toObjects(JobApplication::class.java))
                    } else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }

    fun getJobApplications(jobId: String) {
        viewModelScope.launch {
            db.collection(Constants.JobApplications)
                .whereEqualTo("jobId", jobId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        jobApplications.postValue(it.result.toObjects(JobApplication::class.java))
                    } else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }

    fun getJobByJobId(jobId: String) {
        viewModelScope.launch {
            db.collection(Constants.Jobs)
                .whereEqualTo("jobId", jobId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        job.value = it.result.toObjects(Job::class.java).first()
                    }
                }
        }
    }

    fun addSkill(skill: String, skills: List<String>, onComplete: (Task<Void>) -> Unit) {
        viewModelScope.launch {
            val skillList = skills.toMutableList()
            skillList.add(skill)
            db.collection(Constants.Users)
                .document(auth.uid!!)
                .update(mapOf("skills" to skillList))
                .addOnCompleteListener(onComplete)
        }
    }

    fun deleteSkill(i: Int, skills: List<String>, onComplete: (Task<Void>) -> Unit) {
        viewModelScope.launch {
            val skillList = skills.toMutableList()
            skillList.removeAt(i)
            db.collection(Constants.Users)
                .document(auth.uid!!)
                .update(mapOf("skills" to skillList))
                .addOnCompleteListener(onComplete)
        }
    }

    fun updateAbout(about: String, onComplete: (Task<Void>) -> Unit) {
        viewModelScope.launch {
            db.collection(Constants.Users)
                .document(auth.uid!!)
                .update(mapOf("about" to about))
                .addOnCompleteListener(onComplete)
        }
    }

    fun getJobRecommendations() {
        viewModelScope.launch {
            db.collection(Constants.Jobs)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val jobs = it.result.toObjects(Job::class.java)

                        val skills = mutableListOf<String>()
                        user.value?.skills?.forEach { skill ->
                            skills.addAll(skill.lowercase().split(" "))
                        }

                        val scoreMap = mutableMapOf<String, Double>()

                        val jobDocuments = jobs.map { job ->
                            val tokens = "${job.title} ${job.description}".lowercase().split(" ")
                                .toMutableList()
                            tokens.addAll(job.skillsRequired.map { skill -> skill.lowercase() })
                            tokens
                        }

                        jobs.forEachIndexed { index, job ->
                            val document = jobDocuments[index]
                            var numerator = 0.0
                            skills.forEach { skill ->
                                numerator += tfIdf(skill, document, jobDocuments)
                            }
                            var denominator = 0.0
                            document.forEach { token ->
                                val tokenTfIdf = tfIdf(token, document, jobDocuments)
                                denominator += tokenTfIdf * tokenTfIdf
                            }
                            denominator = sqrt(denominator)
                            val score = (numerator + 1) / (denominator + 1)
                            scoreMap[job.jobId] = score
                        }

                        jobs.sortByDescending { job ->
                            scoreMap[job.jobId]
                        }
                        val recommendations = jobs.subList(0, min(5, jobs.size))
                        jobRecommendations.postValue(recommendations)
                    } else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }

    fun getUserRecommendations(job: Job) {
        viewModelScope.launch {
            db.collection(Constants.Users)
                .whereEqualTo("type", "User")
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val users = it.result.toObjects(User::class.java)
                        val tokens =
                            "${job.title} ${job.description}".lowercase().split(" ").toMutableList()
                        tokens.addAll(job.skillsRequired.map { skill -> skill.lowercase() })

                        val userDocuments = users.map { user ->
                            val skills = mutableListOf<String>()
                            user.skills.forEach { skill ->
                                skills.addAll(skill.lowercase().split(" "))
                            }
                            skills
                        }

                        val scoreMap = mutableMapOf<String, Double>()

                        users.forEachIndexed { index, user ->
                            val document = userDocuments[index]
                            var numerator = 0.0
                            tokens.forEach { token ->
                                numerator += tfIdf(token, document, userDocuments)
                            }
                            var denominator = 0.0
                            document.forEach { skill ->
                                val skillTfIdf = tfIdf(skill, document, userDocuments)
                                denominator += skillTfIdf * skillTfIdf
                            }
                            denominator = sqrt(denominator)
                            val score = (numerator + 1) / (denominator + 1)
                            scoreMap[user.uid] = score
                        }

                        users.sortByDescending { user ->
                            scoreMap[user.uid]
                        }

                        val recommendations = users.subList(0, min(5, users.size))
                        userRecommendations.postValue(recommendations)
                    } else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }

    private fun tfIdf(term: String, document: List<String>, documents: List<List<String>>): Double {
        return termFrequency(term, document) * inverseDocumentFrequency(term, documents)
    }

    private fun termFrequency(term: String, document: List<String>): Double {
        var frequency = 0.0
        document.forEach { token ->
            frequency += findSimilarity(token, term)
        }
        return log10(frequency + 1)
    }

    private fun inverseDocumentFrequency(term: String, documents: List<List<String>>): Double {
        var frequency = 0.0
        documents.forEach { document ->
            var membership = 0.0
            document.forEach { token ->
                membership = max(membership, findSimilarity(token, term))
            }
            frequency += membership
        }
        return log10((documents.size + 1.0) / (frequency + 1.0))
    }

    private fun getLevenshteinDistance(x: String, y: String): Int {
        val m = x.length
        val n = y.length
        val dp = Array(m + 1) { IntArray(n + 1) }
        for (i in 1..m) {
            dp[i][0] = i
        }
        for (j in 1..n) {
            dp[0][j] = j
        }
        var cost: Int
        for (i in 1..m) {
            for (j in 1..n) {
                cost = if (x[i - 1] == y[j - 1]) 0 else 1
                dp[i][j] = min(
                    min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                    dp[i - 1][j - 1] + cost
                )
            }
        }
        return dp[m][n]
    }

    private fun findSimilarity(x: String, y: String): Double {
        val maxLength = max(x.length, y.length)
        var similarity = if (maxLength > 0) {
            (maxLength * 1.0 - getLevenshteinDistance(x, y)) / maxLength * 1.0
        } else 1.0
        if(similarity < 0.5) similarity = 0.0
        return  similarity
    }

    fun searchJobByUser(jobQuery: String) {
        val listToSearch = if (isSearchStarted) {
            jobs.value
        } else cachedJobResults
        viewModelScope.launch {
            if (jobQuery.isEmpty()) {
                jobs.value = cachedJobResults.toMutableList()
                isSearching.value = false
                isSearchStarted = true
                return@launch
            }
            val results = listToSearch.filter {
                it.title.contains(jobQuery.trim(), ignoreCase = true)
            }
            if (isSearchStarted) {
                cachedJobResults = jobs.value
                isSearchStarted = false
            }
            jobs.value = results.toMutableList()
            isSearching.value = true
        }
    }
}