package com.example.gibmejob.screens.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchJobsScreen() {
    var query by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search jobs")
            },
            placeholder = {
                Text(text = "Search jobs..")
            },
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(fontSize = 18.sp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchJobsScreenPreview() {
    SearchJobsScreen()
}