package com.example.gibmejob.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen() {
    Text(text = "Register", fontSize = 42.sp)
}

@Composable
@Preview(showBackground = true)
private fun RegisterScreenPreview() {
    RegisterScreen()
}