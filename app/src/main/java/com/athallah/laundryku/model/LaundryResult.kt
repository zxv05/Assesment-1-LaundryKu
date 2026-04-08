package com.athallah.laundryku.model

data class LaundryResult(
    val customerName: String,
    val serviceName: String,
    val weightInput: String,
    val totalPrice: Int
)
