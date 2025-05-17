package com.example.ndis_client.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderSection(
    userName: String,
    userId: String,
    daysLeft: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Morning,",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = userName,
                fontSize = 22.sp,
                color = Color(0xFF2C6DA6), // Custom blue like the image
                fontWeight = FontWeight.Bold
            )
            Text(
                text = userId,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // Right: Pill with days left
        Box(
            modifier = Modifier
                .border(width = 2.dp, color = Color(0xFFB9D4B4), shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 24.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$daysLeft",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3D7A47), // green text
                    fontSize = 22.sp
                )
                Text(
                    text = "Days Left",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
