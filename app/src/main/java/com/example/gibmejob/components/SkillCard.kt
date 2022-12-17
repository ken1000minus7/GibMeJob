package com.example.gibmejob.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gibmejob.model.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillCard(skill: String, isLoggedInUser: Boolean, onDelete: () -> Unit) {

    var dialogOpen by remember {
        mutableStateOf(false)
    }

    if(dialogOpen) {
        Dialog(onDismissRequest = { dialogOpen = false }) {
            ElevatedCard(onClick = { /*TODO*/ }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Are you sure you want to delete the skill '$skill'?", fontSize = 22.sp, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(onClick = {
                            dialogOpen = false
                            onDelete()
                        }) {
                            Text(text = "Yes", fontSize = 18.sp)
                        }
                        Button(onClick = { dialogOpen = false }) {
                            Text(text = "No", fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(2.dp, color = Color.Black, shape = RoundedCornerShape(25.dp))
            .background(color = Color(0xFFB5F7F7), shape = RoundedCornerShape(25.dp))
            .padding(horizontal = 10.dp, vertical = 2.dp)
    ) {
        Text(text = skill, fontSize = 16.sp, textAlign = TextAlign.Right)
        if(isLoggedInUser) {
            IconButton(onClick = { dialogOpen = true }, modifier = Modifier.padding(0.dp)) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = Color.Red, modifier = Modifier.padding(0.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SkillCardPreview() {
    SkillCard(skill = "c++", isLoggedInUser = true) {}
}