package com.example.gibmejob.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.sql.ClientInfoStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyJobScreen(navHostController : NavHostController) {
Scaffold(floatingActionButton = {
    FloatingActionButton(onClick = { /*TODO*/ }) {
        Icon(imageVector = Icons.Rounded.Add, contentDescription ="")
    }
}) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(it)) {
        LazyColumn{
            items(5){
                CompanyJobCard(
                    navHostController = navHostController,
                    jobName = "Job ${it + 1}",
                    numberOfApplicants = listOf(100,12,13,15,156).random(),
                    jobId = (it+1).toString(),
                    status  = listOf("Rejected", "In Progress", "Accepted").random() //TODO : Use definite values
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
        .background(Color.Gray)){
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