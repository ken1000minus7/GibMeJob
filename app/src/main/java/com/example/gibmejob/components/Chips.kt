package com.example.gibmejob.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Chips(title : String,
          onRemove : ()-> Unit) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colorScheme.secondary)
            .border(
                border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title)
            Icon(
                modifier = Modifier.clickable { onRemove() },
                imageVector = Icons.Rounded.RemoveCircle, contentDescription = ""
            )
        }
    }
}

@Composable
fun Chip(title: String) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colorScheme.secondary)
            .border(
                border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(5.dp)
    ) {
        Text(text = title)
    }
}
