package com.example.ndis_client.data.remote

import com.example.ndis_client.data.models.Provider
import com.example.ndis_client.data.models.Role
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("providers/available")
    suspend fun getProviders(
        @Query("date") date: String,
        @Query("time") time: String,
        @Query("type") type: String
    ): List<Provider>

    @GET("roles/top10")
    suspend fun getTopRoles(
        @Query("ndisNumber") ndisNumber: String
    ): List<Role>
}
