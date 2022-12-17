package com.example.gibmejob.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.gibmejob.screens.user.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAboutDialog(about: String, onDismissRequest: () -> Unit) {
    var newAbout by remember {
        mutableStateOf(about)
    }

    val context = LocalContext.current
    val userViewModel = viewModel<UserViewModel>()
    Dialog(onDismissRequest = onDismissRequest) {
        ElevatedCard {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Update About", fontSize = 22.sp, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center)
                OutlinedTextField(
                    value = newAbout,
                    onValueChange = { newAbout = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(150.dp),
                    maxLines = 5
                )

                Button(modifier = Modifier.padding(10.dp), onClick = {
                    if(newAbout.isEmpty()) {
                        Toast.makeText(context, "Skill can't be empty", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    userViewModel.updateAbout(newAbout) {
                        if(it.isSuccessful) {
                            Toast.makeText(context, "About updated", Toast.LENGTH_SHORT).show()
                            onDismissRequest()
                        }
                        else {
                            it.exception?.printStackTrace()
                            Toast.makeText(context, it.exception?.message ?: "", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Text(text = "Update", fontSize = 18.sp)
                }
            }
        }
    }
}