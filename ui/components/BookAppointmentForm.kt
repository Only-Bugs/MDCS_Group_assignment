@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ndis_client.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.net.toUri

@Composable
fun BookAppointmentFormScaffold() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Appointment") },
                navigationIcon = {
                    IconButton(onClick = { /* handle back */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            )
        }
    ) { innerPadding ->
        BookAppointmentFormContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(22.dp)
                .verticalScroll(rememberScrollState()),
            context = context,
            coroutineScope = coroutineScope
        )
    }
}

@Composable
fun BookAppointmentFormContent(
    modifier: Modifier = Modifier,
    context: Context,
    coroutineScope: CoroutineScope
) {
    var ndisNumber by remember { mutableStateOf("") }
    var ndisNumberError by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("Select date") }
    var selectedTime by remember { mutableStateOf("Select time") }
    var dateError by remember { mutableStateOf(false) }
    var timeError by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val services = listOf(
        "Audiologist", "Dental", "Dermatologist", "Dietitian", "Speech Therapist", "Occ Therapist",
        "Neurologist", "Ophthalmologist", "GP", "Psychiatrist", "Psychologist", "Blood Test", "Other"
    )
    var serviceExpanded by remember { mutableStateOf(false) }
    var selectedService by remember { mutableStateOf(services.first()) }

    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val today = remember { Calendar.getInstance() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier) {
            Text("Hello User!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Enter your details below to book an appointment.", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = ndisNumber,
                onValueChange = {
                    ndisNumber = it
                    ndisNumberError = it.length != 9
                },
                label = { Text("NDIS Number *") },
                isError = ndisNumberError,
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    if (ndisNumberError) {
                        Text("NDIS number must be exactly 9 digits", color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = serviceExpanded,
                onExpandedChange = { serviceExpanded = !serviceExpanded }
            ) {
                OutlinedTextField(
                    value = selectedService,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("What are you looking for?") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(serviceExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = serviceExpanded,
                    onDismissRequest = { serviceExpanded = false }
                ) {
                    services.forEach { service ->
                        DropdownMenuItem(
                            text = { Text(service) },
                            onClick = {
                                selectedService = service
                                serviceExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        context,
                        { _, y, m, d ->
                            calendar.set(y, m, d)
                            if (calendar.before(today)) {
                                dateError = true
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Cannot select a past date.")
                                }
                            } else {
                                selectedDate = dateFormatter.format(calendar.time)
                                dateError = false
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (dateError) Color.Red else MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Filled.CalendarToday, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(selectedDate)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val calendar = Calendar.getInstance()
                    TimePickerDialog(
                        context,
                        { _, hour, minute ->
                            selectedTime = "%02d:%02d".format(hour, minute)
                            timeError = false
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (timeError) Color.Red else MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Filled.AccessTime, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(selectedTime)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    ndisNumberError = ndisNumber.length != 9
                    dateError = selectedDate == "Select date"
                    timeError = selectedTime == "Select time"

                    if (ndisNumberError || dateError || timeError) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Please complete NDIS number, date, and time to continue.")
                        }
                        return@Button
                    }

                    val query = Uri.encode("top 10 $selectedService providers near me")
                    val url = "https://www.google.com/maps/search/?api=1&query=$query".toUri()
                    val intent = Intent(Intent.ACTION_VIEW, url)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Icon(Icons.Filled.PersonSearch, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Show Top 10 Providers Near Me")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("I need an appointment for...", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Describe your condition or your support need") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 6,
                singleLine = false,
                supportingText = {
                    val charCount = description.length
                    Text("${charCount}/100", modifier = Modifier.align(Alignment.End))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    ndisNumberError = ndisNumber.length != 9
                    dateError = selectedDate == "Select date"
                    timeError = selectedTime == "Select time"

                    if (ndisNumberError || dateError || timeError) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Please fill in all required fields.")
                        }
                        return@Button
                    }

                    showDialog = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Book Appointment")
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Appointment booked successfully!")
                            }
                        }) {
                            Text("OK")
                        }
                    },
                    title = { Text("Success") },
                    text = { Text("Your appointment has been booked!") }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            SnackbarHost(hostState = snackbarHostState)
        }
    }
}
