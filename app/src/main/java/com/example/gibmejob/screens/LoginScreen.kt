package com.example.gibmejob.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to",
            fontSize = 42.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "GibMeJob",
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(15.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(text = "Login as User", fontSize = 22.sp)
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(text = "Login as Company", fontSize = 22.sp)
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(text = "Register", fontSize = 22.sp)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    LoginScreen()
}