package com.example.ndis_client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ndis_client.data.bottomNavItems
import com.example.ndis_client.data.generateBudgetSegments
import com.example.ndis_client.data.generateServicesIncluded
import com.example.ndis_client.data.generateTransactions
import com.example.ndis_client.ui.components.*
import com.example.ndis_client.ui.theme.NDIS_clientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NDIS_clientTheme {
                var selectedIndex by remember { mutableStateOf(0) }
                val navItems = bottomNavItems()
                val selectedColor = Color(0xFF2C6DA6)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            tonalElevation = 8.dp,
                            containerColor = Color.White
                        ) {
                            navItems.forEachIndexed { index, item ->
                                val selected = selectedIndex == index

                                NavigationBarItem(
                                    selected = selected,
                                    onClick = { selectedIndex = index },
                                    icon = {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = item.label,
                                            tint = if (selected) selectedColor else Color.Gray,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = item.label,
                                            color = if (selected) selectedColor else Color.Gray,
                                            style = MaterialTheme.typography.labelMedium
                                        )
                                    },
                                    alwaysShowLabel = true,
                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = Color(0x112C6DA6)
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (selectedIndex) {
                            0 -> DashboardScreen()
                            1 -> BookAppointmentScreen()
                            2 -> MyAppointmentsScreen()
                            3 -> AccountScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Dashboard",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF1E1E1E),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        item {
            HeaderSection(
                userName = "Shreya Shrestha",
                userId = "987654321",
                daysLeft = 289
            )
        }

        item {
            BudgetSection(
                segments = generateBudgetSegments(),
                creditLeft = "$64,251"
            )
        }

        item {
            ServicesIncluded(
                services = generateServicesIncluded()
            )
        }

        item {
            TransactionsSection(
                transactions = generateTransactions()
            )
        }
    }
}

@Composable
fun BookAppointmentScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BookAppointmentFormScaffold()
    }
}

@Composable
fun MyAppointmentsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("My Appointments Page")
    }
}

@Composable
fun AccountScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Account Page")
    }
}
