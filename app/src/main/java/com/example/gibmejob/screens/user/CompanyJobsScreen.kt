package com.example.gibmejob.screens.user

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gibmejob.model.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyJobsScreen(navHostController : NavHostController, userViewModel: UserViewModel) {
    userViewModel.getCompanyJobs(userViewModel.uid)
    val jobs by userViewModel.jobs.collectAsState()
    Log.d("fdfgd",jobs.toString() + userViewModel.uid)
Scaffold(floatingActionButton = {
    FloatingActionButton(onClick = { navHostController.navigate(Routes.CreateCompanyJobScreen) }) {
        Icon(imageVector = Icons.Rounded.Add, contentDescription ="")
    }
}) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(it)) {
        LazyColumn {
            items(jobs){ job ->
                CompanyJobCard(
                    navHostController = navHostController,
                    jobName = "Job ${job.title}",
                    numberOfApplicants =job.totalApplicants,
                    jobId = job.jobId,
                    status = job.status
                )
            }
        }
    }

}
}

@Composable
fun CompanyJobCard(navHostController: NavHostController,
                    jobName: String,
                    jobId: String,
                    numberOfApplicants: Int,
                    status: String
) {
    Box(modifier = Modifier
        .padding(10.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color.Gray)
        .clickable { navHostController.navigate("CompanyJobScreen/${jobId}") }
    ){
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(70.dp)) {
            Text(text = jobName)
            Text(text = "Number of Applicants :$numberOfApplicants")
            Text(text = status)
        }
    }
}