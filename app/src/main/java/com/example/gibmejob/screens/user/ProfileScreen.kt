package com.example.gibmejob.screens.user

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gibmejob.components.CompanyProfile
import com.example.gibmejob.components.UserProfile
import com.example.gibmejob.model.Company
import com.example.gibmejob.model.Routes
import com.example.gibmejob.model.User
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(user: User?, company: Company?, navController: NavController, navControllerSec: NavController) {
    val auth = FirebaseAuth.getInstance()
    val navControllerBackStackEntry by navController.currentBackStackEntryAsState()
    var dialogOpen by remember {
        mutableStateOf(false)
    }
    Log.d("lol",user.toString())
    Log.d("lol",company.toString())
    
    if(dialogOpen) {
        Dialog(onDismissRequest = { dialogOpen = false }) {
            ElevatedCard {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Are you sure you want to logout?", fontSize = 22.sp, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(onClick = {
                            auth.signOut()
                            dialogOpen = false
                            navControllerSec.navigate(Routes.LoginOptionScreen) {
                                popUpTo(navControllerBackStackEntry!!.destination.route!!) {
                                    inclusive = true
                                }
                            }
                        }) {
                            Text(text = "Yes", fontSize = 18.sp)
                        }
                        Button(onClick = { dialogOpen = false }) {
                            Text(text = "No", fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    if(auth.uid == user?.uid || auth.uid == company?.uid) {
                        IconButton(onClick = { dialogOpen = true }) {
                            Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout")
                        }
                    }
                }
            )
        }
    ) { paddingValues->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            user?.let {
                UserProfile(it)
            }
            company?.let {
                CompanyProfile(it)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(user = User("idk","abc@gmail.com","elo"), company = null, navController = rememberNavController(), rememberNavController())
}