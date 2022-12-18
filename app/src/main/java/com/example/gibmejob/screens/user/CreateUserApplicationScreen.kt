package com.example.gibmejob.screens.user

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gibmejob.model.JobApplication
import com.example.gibmejob.ui.theme.GibMeJobTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserApplicationScreen(
    bottomNavController: NavHostController,
    userViewModel: UserViewModel,
    jobId : String
) {
    GibMeJobTheme {
        var email by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var phoneNumber by remember {
            mutableStateOf(TextFieldValue(""))
        }
        userViewModel.getJobByJobId(jobId)
        val user by userViewModel.user.observeAsState()
        val job by userViewModel.job.collectAsState()
        val context = LocalContext.current
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Fill Job Application") },
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
            bottomBar = {},
            floatingActionButton = {
                ExtendedFloatingActionButton(onClick = {
                    val jobApplication = JobApplication(
                        email = email.text,
                        phone = phoneNumber.text,
                        job = job?.title ?: "",
                        jobId = jobId,
                        userId = userViewModel.uid,
                        companyId = job?.companyUid ?: "",
                        userName = userViewModel.name,
                        skills = user?.skills ?: listOf()
                    )
                    userViewModel.addApplication(jobApplication){
                        Toast.makeText(context, "Job Application posted", Toast.LENGTH_SHORT)
                            .show()
                        userViewModel.updateJobApplicantsCount(jobId)
                        bottomNavController.navigateUp()
                    }
                }) {
                    Icon(imageVector = Icons.Rounded.Check, contentDescription = "")
                }
            }
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
                    value = email,
                    label = {
                        Text(text = "Enter email address")
                    }, onValueChange = {
                        email = it
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "emailIcon"
                        )
                    })
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    value = phoneNumber,
                    label = {
                        Text(text = "Enter phone number")
                    },
                    onValueChange = {
                        phoneNumber = it
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "phoneIcon"
                        )
                    })
            }
        }
    }
}
