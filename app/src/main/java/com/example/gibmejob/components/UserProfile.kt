package com.example.gibmejob.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gibmejob.model.User
import com.example.gibmejob.screens.user.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserProfile(user: User) {
    Column {
        val firebaseUser = FirebaseAuth.getInstance()

        Text(text = user.name)
        Button(onClick = {firebaseUser.signOut()}) {
            Row {
                Icon(imageVector = Icons.Rounded.Person, contentDescription = "")
                Text(text = "Logout")
            }
        }
    }
}