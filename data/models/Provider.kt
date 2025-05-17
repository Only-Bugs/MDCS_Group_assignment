package com.example.ndis_client.data.models

data class Provider(
    val id: String,
    val name: String,
    val distance: String? = null,
    val rating: Double? = null
)
