package com.example.gibmejob.screens.user

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Backpack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Details
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gibmejob.components.Chips
import com.example.gibmejob.model.Job
import com.example.gibmejob.ui.theme.GibMeJobTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCompanyJobScreen(userViewModel: UserViewModel, bottomNavController: NavHostController) {
    GibMeJobTheme {
        userViewModel.getCompany()
        var title by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var location by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var jobType by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var jobDesc by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var skill by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var skills by remember {
            mutableStateOf(listOf<String>())
        }
        val context = LocalContext.current
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Create Job") },
                    navigationIcon = {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    bottomNavController.navigateUp()
                                }
                                .padding(10.dp)
                        )
                    })
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        val job = Job(
                            title = title.text,
                            description = jobDesc.text,
                            companyUid = userViewModel.uid,
                            companyName = userViewModel.name!!,
                            skillsRequired = skills,
                            jobType = jobType.text,
                            location = location.text,
                            status = "Open"
                        )
                        userViewModel.addJob(job) {
                            Toast.makeText(context, "Job posted", Toast.LENGTH_SHORT)
                                .show()
                            bottomNavController.navigateUp()
                            userViewModel.getCompanyJobs(userViewModel.uid)
                        }

                    },
                ) {
                    Icon(imageVector = Icons.Rounded.Check, contentDescription = "")
                }
            },
            bottomBar = {}
        ) { paddingValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    value = title,
                    label = {
                        Text(text = "Enter Job Role")
                    }, onValueChange = {
                        title = it
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Engineering,
                            contentDescription = "jobRole"
                        )
                    })
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    value = location,
                    label = {
                        Text(text = "Enter location")
                    },
                    onValueChange = {
                        location = it
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationCity,
                            contentDescription = "location"
                        )
                    })
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    value = jobType,
                    label = {
                        Text(text = "Enter Job Type")
                    },
                    onValueChange = {
                        jobType = it
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Backpack,
                            contentDescription = "jobType"
                        )
                    })
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp),
                    value = jobDesc,
                    label = {
                        Text(text = "Enter Job Description")
                    },
                    onValueChange = {
                        jobDesc = it
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Details,
                            contentDescription = "jobDesc"
                        )
                    })
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    value = skill,
                    label = {
                        Text(text = "Enter Skills")
                    },
                    onValueChange = {
                        skill = it
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = "skills"
                        )
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = "",
                            modifier = Modifier.clickable {
                                val newSkills = skills.toMutableList()
                                newSkills.add(skill.text)
                                skills = newSkills
                            })
                    }
                )
                LazyVerticalGrid(columns = GridCells.Adaptive(100.dp)) {
                    items(skills) {
                        Chips(title = it) {
                            val newSkills = skills.toMutableList()
                            newSkills.remove(it)
                            skills = newSkills
                        }
                    }
                }
            }
        }
    }
}