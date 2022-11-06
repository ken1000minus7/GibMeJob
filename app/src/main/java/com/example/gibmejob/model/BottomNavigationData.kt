package com.example.gibmejob.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationData(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    object SearchJobs: BottomNavigationData("Search Jobs", Icons.Default.Search, Routes.SearchJobsScreen)
    object UserApplications: BottomNavigationData("Your Applications", Icons.Default.Assignment, Routes.UserApplicationsScreen)
    object UserProfile: BottomNavigationData("Profile", Icons.Default.AccountCircle, Routes.UserProfile)

    object CurrentJobs: BottomNavigationData("Your Jobs", Icons.Default.Business, Routes.CurrentJobs)
    object CompanyProfile: BottomNavigationData("Company Profile", Icons.Default.Group, Routes.CompanyProfile)
}