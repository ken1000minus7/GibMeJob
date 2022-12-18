package com.example.gibmejob.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gibmejob.R
import com.example.gibmejob.model.Company
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CompanyProfile(company: Company) {
    val auth = FirebaseAuth.getInstance()
    var aboutDialogOpen by remember {
        mutableStateOf(false)
    }

    if(aboutDialogOpen) {
        EditAboutDialog(about = company.about) {
            aboutDialogOpen = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = company.name, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
                Text(text = company.email, fontSize = 15.sp, fontWeight = FontWeight.Light)
            }
            Image(
                painter = painterResource(id = R.drawable.company),
                contentDescription = company.name,
                modifier = Modifier
                    .height(70.dp)
                    .width(70.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(50)),
                contentScale = ContentScale.Crop
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "About", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            if (auth.uid == company.uid) {
                IconButton(onClick = { aboutDialogOpen = true }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "edit")
                }
            }
        }
        Text(
            text = company.about.ifEmpty { "(Nothing added yet)" },
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CompanyProfilePreview() {
    CompanyProfile(Company(
        "asdfsdfsdsf",
        "Hello",
        "Hi"
    ))
}