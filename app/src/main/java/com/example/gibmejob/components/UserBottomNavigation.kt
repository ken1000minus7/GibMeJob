package com.example.gibmejob.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gibmejob.model.BottomNavigationData

@Composable
fun UserBottomNavigation(navController: NavController, type: String) {
    val bottomNavigationItems = if(type == "User") listOf(
        BottomNavigationData.SearchJobs,
        BottomNavigationData.UserApplications,
        BottomNavigationData.UserProfile
    ) else listOf(
        BottomNavigationData.CurrentJobs,
        BottomNavigationData.CompanyProfile
    )

    NavigationBar {
        val navControllerBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navControllerBackStackEntry?.destination?.route
        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(text = item.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            )
        }
    }
}