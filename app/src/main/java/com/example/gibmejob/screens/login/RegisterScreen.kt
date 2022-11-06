package com.example.gibmejob.screens.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
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
fun RegisterScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val loginViewModel = viewModel<LoginViewModel>()
    val navControllerBackStackEntry by navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("GibMeJob", Context.MODE_PRIVATE)
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val confirmPassword = remember {
        mutableStateOf("")
    }
    var type by remember {
        mutableStateOf("User")
    }
    var typeExpanded by remember {
        mutableStateOf(false)
    }
    val options = listOf(
        "User",
        "Company"
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Register", fontSize = 45.sp, modifier = Modifier.padding(10.dp))
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
                    value = email.value,
                    onValueChange = {
                        email.value = it
                    },
                    label = { Text(text = "Email") },
                    textStyle = TextStyle(fontSize = 22.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                OutlinedTextField(
                    value = password.value,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = {
                        password.value = it
                    },
                    label = { Text(text = "Password") },
                    textStyle = TextStyle(fontSize = 22.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                OutlinedTextField(
                    value = confirmPassword.value,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = {
                        confirmPassword.value = it
                    },
                    label = { Text(text = "Confirm Password") },
                    textStyle = TextStyle(fontSize = 22.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = typeExpanded,
                    onExpandedChange = {
                        typeExpanded = it
                    }
                ) {
                    options.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(text = it)
                            },
                            onClick = {
                                type = it
                                typeExpanded = false
                            }
                        )
                    }
                }
                Button(
                    onClick = {
                        if(password.value != confirmPassword.value) {
                            Toast.makeText(context, "Password and confirm password do not match", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        loginViewModel.register(email.value, password.value) {
                            if(it.isSuccessful) {
                                loginViewModel.addUser(it.result.user!!) {
                                    navController.navigate(Routes.UserScreen) {
                                        popUpTo(navControllerBackStackEntry!!.destination.route!!) {
                                            inclusive = true
                                        }
                                    }
                                }
                                Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
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
private fun RegisterScreenPreview() {
    RegisterScreen(rememberNavController())
}