package com.example.gibmejob.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gibmejob.model.Routes
import com.example.gibmejob.screens.user.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSkillDialog(skills: List<String>, onDismissRequest: () -> Unit) {
    var skill by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val userViewModel = viewModel<UserViewModel>()
    Dialog(onDismissRequest = onDismissRequest) {
        ElevatedCard {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Add Skill", fontSize = 22.sp, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center)
                OutlinedTextField(value = skill, onValueChange = { skill = it }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp))

                Button(modifier = Modifier.padding(10.dp), onClick = {
                    if(skill.isEmpty()) {
                        Toast.makeText(context, "Skill can't be empty", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    userViewModel.addSkill(skill, skills) {
                        if(it.isSuccessful) {
                            Toast.makeText(context, "Skill added", Toast.LENGTH_SHORT).show()
                            onDismissRequest()
                        }
                        else {
                            it.exception?.printStackTrace()
                            Toast.makeText(context, it.exception?.message ?: "", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Text(text = "Add", fontSize = 18.sp)
                }
            }
        }
    }
}