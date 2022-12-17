package com.example.gibmejob.screens.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gibmejob.model.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    val user: MutableLiveData<User?> = MutableLiveData(null)
    val company: MutableLiveData<Company?> = MutableLiveData(null)
    val jobs: MutableLiveData<MutableList<Job>> = MutableLiveData(mutableListOf())
    val jobApplications: MutableLiveData<MutableList<JobApplication>> = MutableLiveData(mutableListOf())

    fun getUser() {
        viewModelScope.launch {
            db.collection("Users")
                .document(auth.uid!!)
                .addSnapshotListener { value, error ->
                    if(value?.exists() == true) {
                        user.postValue(value.toObject(User::class.java))
                    }
                    else {
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
                    if(value?.exists() == true) {
                        company.postValue(value.toObject(Company::class.java))
                    }
                    else {
                        error?.printStackTrace()
                    }
                }
        }
    }

    fun addJob(job: Job, onComplete: (Task<DocumentReference>) -> Unit) {
        viewModelScope.launch {
            db.collection(Constants.Jobs)
                .add(job)
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        job.jobId = it.result.id
                        db.collection(Constants.Jobs)
                            .document(job.jobId)
                            .set(job, SetOptions.merge())
                    }
                    onComplete(it)
                }
        }
    }

    fun addApplication(jobApplication: JobApplication, onComplete: (Task<DocumentReference>) -> Unit) {
        viewModelScope.launch {
            db.collection(Constants.JobApplications)
                .add(jobApplication)
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        jobApplication.applicationId = it.result.id
                        db.collection(Constants.Jobs)
                            .document(jobApplication.applicationId)
                            .set(jobApplication, SetOptions.merge())
                    }
                    onComplete(it)
                }
        }
    }

    fun getAllJobs() {
        viewModelScope.launch {
            db.collection(Constants.Jobs)
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        jobs.postValue(it.result.toObjects(Job::class.java))
                    }
                    else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }

    fun getCompanyJobs(companyId: String) {
        viewModelScope.launch {
            db.collection(Constants.Jobs)
                .whereEqualTo("companyId",companyId)
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        jobs.postValue(it.result.toObjects(Job::class.java))
                    }
                    else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }

    fun getUserJobApplications(userId: String) {
        viewModelScope.launch {
            db.collection(Constants.JobApplications)
                .whereEqualTo("userId",userId)
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        jobApplications.postValue(it.result.toObjects(JobApplication::class.java))
                    }
                    else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }

    fun getJobApplications(jobId: String) {
        viewModelScope.launch {
            db.collection(Constants.JobApplications)
                .whereEqualTo("jobId",jobId)
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        jobApplications.postValue(it.result.toObjects(JobApplication::class.java))
                    }
                    else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }
}