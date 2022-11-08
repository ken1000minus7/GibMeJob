package com.example.gibmejob.screens.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.google.android.gms.tasks.Task
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
    val options = listOf(
        "User",
        "Company"
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Register",
            fontSize = 45.sp,
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
                    value = email.value,
                    onValueChange = {
                        email.value = it
                    },
                    label = { Text(text = "Email") },
                    textStyle = TextStyle(fontSize = 20.sp),
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
                    visualTransformation = PasswordVisualTransformation(),
                    value = confirmPassword.value,
                    onValueChange = {
                        confirmPassword.value = it
                    },
                    label = { Text(text = "Confirm Password") },
                    textStyle = TextStyle(fontSize = 22.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Text(text = "Who are you?", fontSize = 22.sp)
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    options.forEach {
                        val icon = if(it == "User") Icons.Default.Person else Icons.Default.Group
                        val selected = type == it
                        Column(
                            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    type = it
                                }
                                .border(
                                    width = 5.dp,
                                    color = if(selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                ).padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = it,
                                modifier = Modifier.size(80.dp)
                            )
                            Text(text = it)
                        }
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
                                val onComplete = {_: Task<Void> ->
                                    navController.navigate(Routes.UserScreen) {
                                        popUpTo(navControllerBackStackEntry!!.destination.route!!) {
                                            inclusive = true
                                        }
                                    }
                                    sharedPreferences.edit()
                                        .putString("type",type)
                                        .apply()
                                }
                                if(type == "User") {
                                    loginViewModel.addUser(it.result.user!!, onComplete)
                                }
                                else {
                                    loginViewModel.addCompany(it.result.user!!, onComplete)
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