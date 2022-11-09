package com.example.gibmejob.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserJobScreen(
    jobId: String, bottomNavController: NavController
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
            .fillMaxWidth()
            .height(400.dp)
            .padding(it)
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                Color.Gray
            )
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Company #$jobId", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Text(text = "Role: Role $jobId", fontSize = 20.sp)
                Text(text = "Job Description:",fontSize = 20.sp)
                Text(text = "lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..lorem..ipsum..")
                Text(text = "")
                Button(onClick = {  }) {
                    Text(text = "Apply")
                }
        }
    }
}