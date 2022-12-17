package com.example.gibmejob.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gibmejob.R
import com.example.gibmejob.model.User
import com.example.gibmejob.screens.user.UserViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserProfile(user: User) {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    val userViewModel = viewModel<UserViewModel>()
    
    var aboutDialogOpen by remember {
        mutableStateOf(false)
    }

    var skillDialogOpen by remember {
        mutableStateOf(false)
    }
    
    if(aboutDialogOpen) {
        EditAboutDialog(about = user.about) {
            aboutDialogOpen = false
        }
    }
    
    if(skillDialogOpen) {
        AddSkillDialog(user.skills) {
            skillDialogOpen = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = user.name, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
                Text(text = user.email, fontSize = 15.sp, fontWeight = FontWeight.Light)
            }
            Image(
                painter = painterResource(id = R.drawable.person),
                contentDescription = user.name,
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
            if(auth.uid == user.uid) {
                IconButton(onClick = { aboutDialogOpen = true }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "edit")
                }
            }
        }
        Text(
            text = user.about.ifEmpty { "(Nothing added yet)" },
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Skills", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            if(auth.uid == user.uid) {
                IconButton(onClick = { skillDialogOpen = true }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "edit")
                }
            }
        }
        if(user.skills.isEmpty()) {
            Text(text = "No skills added yet")
        }
        else {
            FlowRow(
                mainAxisSpacing = 5.dp,
                crossAxisSpacing = 5.dp
            ) {
                user.skills.forEachIndexed { i, skill->
                    SkillCard(skill = skill, isLoggedInUser = auth.uid == user.uid) {
                        userViewModel.deleteSkill(i, user.skills) {
                            if(it.isSuccessful) {
                                Toast.makeText(context, "Skill deleted", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserProfilePreview() {
    UserProfile(user = User("idk","abc@gmail.com","Elon Musk"))
}