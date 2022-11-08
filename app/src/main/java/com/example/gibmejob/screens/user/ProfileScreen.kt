package com.example.gibmejob.screens.user

import androidx.compose.runtime.Composable
import com.example.gibmejob.components.CompanyProfile
import com.example.gibmejob.components.UserProfile
import com.example.gibmejob.model.Company
import com.example.gibmejob.model.User

@Composable
fun ProfileScreen(user: User?, company: Company?) {
    user?.let {
        UserProfile(it)
    }
    company?.let {
        CompanyProfile(it)
    }
}