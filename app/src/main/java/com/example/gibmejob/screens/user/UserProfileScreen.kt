package com.example.gibmejob.screens.user

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserProfileScreen() {
    val userViewModel= viewModel<UserViewModel>()
    val user by userViewModel.user.observeAsState()
    Text(text = user?.name ?: "sadge")
}