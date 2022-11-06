package com.example.gibmejob.screens.user

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gibmejob.components.UserBottomNavigation
import com.example.gibmejob.model.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(navController: NavController, type: String) {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            UserBottomNavigation(navController = bottomNavController, type = type)
        }
    ) {
        NavHost(
            navController = bottomNavController,
            startDestination = Routes.SearchJobsScreen,
            modifier = Modifier.padding(it)
        ) {
            composable(Routes.SearchJobsScreen) {
                SearchJobsScreen()
            }
            composable(Routes.UserApplicationsScreen) {
                UserApplicationsScreen()
            }
            composable(Routes.UserProfile) {
                UserProfileScreen()
            }
            composable(Routes.CurrentJobs) {
                Text(text = "jobs")
            }
            composable(Routes.CompanyProfile) {
                Text(text = "profile")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun UserScreenPreview() {
    UserScreen(rememberNavController(),"User")
}