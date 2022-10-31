package com.example.gibmejob.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val confirmPassword = remember {
        mutableStateOf("")
    }
    Column {
        Text(text = "Register", fontSize = 42.sp)
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
                    textStyle = TextStyle(fontSize = 22.sp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun RegisterScreenPreview() {
    RegisterScreen()
}