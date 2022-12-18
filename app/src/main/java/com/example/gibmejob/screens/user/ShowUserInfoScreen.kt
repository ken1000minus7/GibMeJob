package com.example.gibmejob.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gibmejob.components.Chip
import com.example.gibmejob.ui.theme.GibMeJobTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowUserInfoScreen(userId: String,
                       userViewModel: UserViewModel) {
    GibMeJobTheme {
        Scaffold (floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { },
                icon = {
                    Icon(imageVector = Icons.Rounded.Check, contentDescription = "")
                },
                onClick = { })
        }) {
            userViewModel.getUserJobApplications(userId)
            val jobApplication by userViewModel.userShowApplications.observeAsState()
            LazyColumn(Modifier.padding(it)) {
                items(jobApplication?.toList() ?: listOf()){ jobApplication ->
                    Column ( modifier = Modifier.fillMaxSize() ) {
                        Row (modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Name")
                            Text(text = jobApplication.userName)
                        }
                        Row (modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween){
                            Text(text = "Email")
                            Text(text = jobApplication.email)
                        }
                        Row (modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween){
                            Text(text = "Phone")
                            Text(text = jobApplication.phone)
                        }
                        Column {
                            Text(text = "Skills")
                            LazyVerticalGrid(columns = GridCells.Fixed(3), Modifier.height(100.dp)){
                                items(jobApplication.skills){
                                    Chip(title = it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}