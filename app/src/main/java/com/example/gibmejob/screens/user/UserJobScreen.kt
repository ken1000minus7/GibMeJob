package com.example.gibmejob.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserJobScreen(jobId: String, navController: NavController, bottomNavController: NavController
    ) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Job $jobId")},
            navigationIcon = {
                Icon(Icons.Rounded.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            bottomNavController.navigateUp()
                        })
            })
    }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                Color.Gray
            )
            .padding(10.dp)) {

            Text(text = "Company #$jobId", fontSize = 15.sp)
            Text(text = "Role: Role $jobId")
            Text(text = "Job Description:", fontWeight = FontWeight.Bold)
            Text(text = "lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..")
            
        }
    }
}