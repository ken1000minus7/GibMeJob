package com.example.gibmejob.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gibmejob.model.User
import com.example.gibmejob.screens.user.UserViewModel

@Composable
fun UserProfile(user: User) {
    Text(text = user.name)
}