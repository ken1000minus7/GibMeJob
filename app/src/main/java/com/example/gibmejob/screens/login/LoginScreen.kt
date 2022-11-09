package com.example.gibmejob.screens.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gibmejob.model.Routes
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val navControllerBackStackEntry by navController.currentBackStackEntryAsState()
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("GibMeJob", Context.MODE_PRIVATE)
    val loginViewModel = viewModel<LoginViewModel>()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            fontSize = 41.sp,
            modifier = Modifier.padding(10.dp),
            fontWeight = FontWeight.Bold
        )
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = { Text(text = "Email") },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                OutlinedTextField(
                    value = password,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = {
                        password = it
                    },
                    label = { Text(text = "Password") },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Button(
                    onClick = {
                        if(email.isEmpty() || password.isEmpty()) {
                            Toast.makeText(context, "One or more fields are empty", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        loginViewModel.login(email, password) {result ->
                            if(result.isSuccessful) {
                                loginViewModel.getLoggedInUser {
                                    if(it.isSuccessful) {
                                        val snapshot = it.result
                                        sharedPreferences.edit()
                                            .putString("type", snapshot.getString("type"))
                                            .apply()
                                        navController.navigate(Routes.UserScreen) {
                                            popUpTo(navControllerBackStackEntry!!.destination.route!!) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                Toast.makeText(context, result.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(text = "Submit", fontSize = 25.sp)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}