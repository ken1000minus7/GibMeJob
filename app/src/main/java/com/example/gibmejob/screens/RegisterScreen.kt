package com.example.gibmejob.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val confirmPassword = remember {
        mutableStateOf("")
    }
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
                    modifier = Modifier.fillMaxWidth()
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
                    modifier = Modifier.fillMaxWidth()
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
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp)
                )
                Button(
                    onClick = {
                        if(password.value != confirmPassword.value) {
                            Toast.makeText(context, "Password and confirm password do not match", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        auth.createUserWithEmailAndPassword(email.value, password.value).addOnCompleteListener {
                            if(it.isSuccessful) {
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
    RegisterScreen()
}