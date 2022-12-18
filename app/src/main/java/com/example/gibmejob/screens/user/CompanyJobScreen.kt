package com.example.gibmejob.screens.user

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gibmejob.components.Chip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyJobScreen(
    jobId: String,
    navController: NavController,
    userViewModel: UserViewModel
) {
    Log.d("jobId",jobId)
    userViewModel.getJobByJobIb(jobId)
    val job by userViewModel.job.collectAsState()
    Scaffold(topBar = {}) {
       LazyColumn (Modifier.padding(it)) {
            items(job){ jobRes ->
                IndividualJobDetails(
                    role = jobRes.jobType,
                    applicants = jobRes.totalApplicants,
                    jobDesc = jobRes.description,
                    status = jobRes.status
                )
            }
           item {
               Text(text = "Skills required")
           }
        }
    }
}

@Composable
fun IndividualJobDetails(
    role: String,
    applicants: Int,
    jobDesc: String,
    status: String
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clip(RoundedCornerShape(10.dp))
        .border(width = 2.dp, color = Color.Gray)){
        Column {
            Row {
                Text(text = "Role")
                Text(text = role)
            }
            Row {
                Text(text = "Number of applicants")
                Text(text = applicants.toString())
            }
            Row {
                Text(text = "Job Description")
                Text(text = jobDesc)
            }
            Row {
                Text(text = "Job Description")
                Text(text = jobDesc)
            }
        }
    }
}