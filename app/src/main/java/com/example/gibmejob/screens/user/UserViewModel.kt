package com.example.gibmejob.screens.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gibmejob.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    val user: MutableLiveData<User> = MutableLiveData()

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
}