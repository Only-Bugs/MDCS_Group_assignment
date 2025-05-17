package com.example.ndis_client.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ndis_client.R

data class ServiceItem(
    val icon: androidx.compose.ui.graphics.painter.Painter,
    val label: String
)

data class Transaction(
    val title: String,
    val subtitle: String,
    val amount: String,
    val iconRight: androidx.compose.ui.graphics.painter.Painter
)

data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)


val selectedColor = Color(0xFF2C6DA6)

@Composable
fun generateTransactions(): List<Transaction> {
    val data = listOf(
        Triple("Dr. Dave Kurt", "General Clinics Pvt Ltd.", "-$55"),
        Triple("Mr. Kelly Rao", "Sunshine Dental", "-$80"),
        Triple("Ms. Reema", "Optic Vision", "-$40")
    )
    return data.map { (name, sub, amt) ->
        Transaction("To $name", sub, amt, painterResource(R.drawable.ic_download))
    }
}

@Composable
fun generateBudgetSegments(): List<Pair<Float, Color>> {
    return listOf(
        37f to Color(0xFFD32F2F),
        26f to Color(0xFF03A9F4),
        22f to Color(0xFFFF9800),
        15f to Color(0xFF4CAF50)
    )
}

@Composable
fun generateServicesIncluded(): List<ServiceItem> {
    return listOf(
        ServiceItem(painterResource(id = R.drawable.ic_local_help), "Local help"),
        ServiceItem(painterResource(id = R.drawable.ic_mechanic), "Mechanic"),
        ServiceItem(painterResource(id = R.drawable.ic_local_help), "Plumber"),
        ServiceItem(painterResource(id = R.drawable.ic_mechanic), "Pet Care"),
        ServiceItem(painterResource(id = R.drawable.ic_local_help), "Chemist"),
        ServiceItem(painterResource(id = R.drawable.ic_more), "20+ More")
    )
}

fun bottomNavItems() = listOf(
    BottomNavItem("Home", Icons.Default.Home),
    BottomNavItem("Book", Icons.Default.CalendarToday),
    BottomNavItem("history", Icons.Default.List),
    BottomNavItem("Account", Icons.Default.AccountCircle)
)
