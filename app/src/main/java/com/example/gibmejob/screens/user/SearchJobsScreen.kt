package com.example.gibmejob.screens.user

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
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gibmejob.model.Job

@Composable
fun SearchJobsScreen(navHostController: NavHostController,
    viewModel: UserViewModel) {
    viewModel.getAllJobs()
    val jobs by viewModel.jobs.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBox(viewModel)
        LazyColumn{
            items(jobs){
                JobCard(
                    navHostController = navHostController,
                    jobName = it.title,
                    companyName = "Company ${it.companyName}",
                    jobId = it.jobId
                )
            }
        }
    }

}



@Composable
fun JobCard(navHostController: NavHostController,
            jobName: String,
            companyName: String,
            jobId: String
    ) {
    Box(modifier = Modifier
        .padding(10.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color.Gray)
        .clickable { navHostController.navigate("UserJobScreen/${jobId}") }){
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(60.dp)) {
            Text(text = jobName)
            Text(text = companyName)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBox(viewModel: UserViewModel) {
    var query by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.searchJobByUser(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search jobs")
            },
            placeholder = {
                Text(text = "Search jobs..")
            },
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(fontSize = 18.sp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchJobsScreenPreview() {
    SearchJobsScreen(rememberNavController(), UserViewModel())
}