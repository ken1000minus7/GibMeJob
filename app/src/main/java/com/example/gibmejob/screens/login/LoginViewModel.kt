package com.example.gibmejob.screens.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gibmejob.model.Company
import com.example.gibmejob.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

// lol no need to even make viewmodel but idk why i made
class LoginViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun register(email: String, password: String, onComplete: (Task<AuthResult>) -> Unit = {}) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(onComplete)
    }

    fun login(email: String, password: String, onComplete: (Task<AuthResult>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(onComplete)
    }

    fun getLoggedInUser(onComplete: (Task<DocumentSnapshot>) -> Unit) {
        db.collection("Users")
            .document(auth.currentUser!!.uid)
            .get()
            .addOnCompleteListener(onComplete)
    }

    fun addUser(firebaseUser: FirebaseUser, name: String, onComplete: (Task<Void>) -> Unit) {
        val user = User(
            uid = firebaseUser.uid,
            email = firebaseUser.email!!,
            name = name,
            photoUrl = firebaseUser.photoUrl?.toString()
        )
        db.collection("Users")
            .document(user.uid)
            .set(user)
            .addOnCompleteListener(onComplete)
    }

    fun addCompany(firebaseUser: FirebaseUser, name: String, onComplete: (Task<Void>) -> Unit) {
        val company = Company(
            uid = firebaseUser.uid,
            email = firebaseUser.email!!,
            name = name,
            photoUrl = firebaseUser.photoUrl?.toString()
        )
        db.collection("Users")
            .document(company.uid)
            .set(company)
            .addOnCompleteListener(onComplete)
    }
}