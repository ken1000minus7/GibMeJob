package com.example.gibmejob.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class UserBottomNavigationData(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    object SearchJobs: UserBottomNavigationData("Search Jobs", Icons.Default.Search, Routes.SearchJobsScreen)
    object UserApplications: UserBottomNavigationData("Your Applications", Icons.Default.Assignment, Routes.UserApplicationsScreen)
    object UserProfile: UserBottomNavigationData("Profile", Icons.Default.AccountCircle, Routes.UserProfile)
}