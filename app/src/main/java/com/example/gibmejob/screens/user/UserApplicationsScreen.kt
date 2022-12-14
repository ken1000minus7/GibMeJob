package com.example.gibmejob.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserApplicationsScreen(navHostController : NavHostController, viewModel: UserViewModel) {
    Scaffold(topBar = {}) {
        viewModel.getUserJobApplications(viewModel.uid)
        val userJobApplications by viewModel.userShowApplications.observeAsState()
        Column(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(userJobApplications?.toList() ?: listOf()) { jobApplication ->
                    ApplicationCard(
                        jobName = jobApplication.job,
                        companyName = jobApplication.companyName,
                        status = jobApplication.selected.toString()
                    )
                }
            }
        }
    }
}

@Composable
fun ApplicationCard(
    companyName: String,
    jobName: String,
    status: String
) {
    Box(modifier = Modifier
        .padding(10.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color.Gray)){
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(70.dp)) {
            Text(text = companyName)
            Text(text = jobName)
            Text(text = status)
        }
    }
}