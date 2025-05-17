package com.example.ndis_client.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ndis_client.R



fun extractRecipientInitial(title: String): String {
    return title
        .removePrefix("To ")                         // Remove "To " at start
        .split(" ")                                  // Split into words
        .firstOrNull { it.firstOrNull()?.isLetter() == true } // Take first valid word
        ?.firstOrNull()                              // Get first letter of that word
        ?.toString()
        ?.uppercase() ?: "?"                         // Default to "?" if failed
}


@Composable
fun TransactionsSection(transactions: List<Transaction>) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Transactions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(transactions) { transaction ->
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

        // Middle text
        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
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

        // Amount + download icon
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
