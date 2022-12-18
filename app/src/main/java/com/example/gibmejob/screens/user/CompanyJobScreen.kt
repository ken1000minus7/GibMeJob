package com.example.gibmejob.screens.user

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gibmejob.R
import com.example.gibmejob.components.Chip
import com.example.gibmejob.model.User
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun CompanyJobScreen(
    jobId: String,
    navController: NavController,
    userViewModel: UserViewModel
) {
    val statusState by remember {
        mutableStateOf("Open")
    }
    userViewModel.getJobByJobId(jobId)
    val job by userViewModel.job.collectAsState()

    Scaffold(topBar = {}, modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            job?.let { jobRes->
                userViewModel.getUserRecommendations(jobRes)
                val userRecommendations by userViewModel.userRecommendations.observeAsState()
                Log.d("lol", userRecommendations?.toString() ?: "")

                Text(
                    text = jobRes.status,
                    color = if (jobRes.status == statusState) {
                        Color.Green
                    } else {
                        Color.Yellow
                    },
                    fontSize = 20.sp,
                )
                IndividualJobDetails(
                    role = jobRes.jobType,
                    applicants = jobRes.totalApplicants,
                    jobDesc = jobRes.description
                )
                Text(text = "Skills required")
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .height(50.dp)
                ) {
                    items(jobRes.skillsRequired) {
                        Chip(title = it)
                    }
                }
                val pagerState = rememberPagerState()
//                TabRow(selectedTabIndex = pagerState.currentPage) {
//                    val scope = rememberCoroutineScope()
//                    Tab(selected = pagerState.currentPage == 0, onClick = {
//                        scope.launch {
//                            pagerState.animateScrollToPage(0)
//                        }
//                    }) {
//                        Text(text = "Recommendations")
//                    }
//                    Tab(selected = pagerState.currentPage == 1, onClick = {
//                        scope.launch {
//                            pagerState.animateScrollToPage(1)
//                        }
//                    }) {
//                        Text(text = "Applications")
//                    }
//                }
                HorizontalPager(count = 2, state = pagerState) { page->
                    when(page) {
                        0 -> {
                            LazyColumn {
                                items(userRecommendations ?: mutableListOf()) { user->
                                    UserCard(user = user)
                                }
                            }
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IndividualJobDetails(
    role: String,
    applicants: Int,
    jobDesc: String
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Role ")
                Text(text = role)
            }
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Number of applicants ")
                Text(text = applicants.toString())
            }
            Spacer(Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Job Description ")
                Text(text = jobDesc)
            }
        }

    }
}

@Composable
fun UserCard(user: User) {
    ElevatedCard(modifier = Modifier.padding(10.dp)) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = user.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = user.email, fontSize = 15.sp)
            }
            Image(
                painter = painterResource(id = R.drawable.person),
                contentDescription = "" ,
                modifier = Modifier.height(30.dp).width(30.dp).border(1.dp, Color.Black, RoundedCornerShape(50)),
                contentScale = ContentScale.Crop
            )
        }
    }
}