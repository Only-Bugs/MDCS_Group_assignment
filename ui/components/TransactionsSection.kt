package com.example.ndis_client.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.ndis_client.data.Transaction

fun extractRecipientInitial(title: String): String {
    val honorifics = setOf("Mr.", "Ms.", "Mrs.", "Dr.", "Miss")

    return title
        .removePrefix("To ")
        .split(" ")
        .firstOrNull { it.isNotBlank() && it !in honorifics }
        ?.firstOrNull()
        ?.uppercaseChar()
        ?.toString() ?: "?"
}

@Composable
fun TransactionsSection(transactions: List<Transaction>) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Transactions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            transactions.forEach { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(color = Color(0xFF2C6DA6), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = extractRecipientInitial(transaction.title),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        // Middle section: Title + Subtitle
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = transaction.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Text(
                text = transaction.subtitle,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = transaction.amount,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = transaction.iconRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
