package com.example.gibmejob.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gibmejob.model.Company
import com.example.gibmejob.screens.user.UserViewModel

@Composable
fun CompanyProfile(company: Company) {
    Text(text = company.name)
}

@Preview
@Composable
fun CompanyProfilePreview() {
    CompanyProfile(Company(
        "asdfsdfsdsf",
        "Hello",
        "Hi"
    ))
}