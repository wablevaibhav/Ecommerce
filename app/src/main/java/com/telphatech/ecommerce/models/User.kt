package com.telphatech.ecommerce.models

data class User(
    val userName: String? = "",
    val userPhoneNumber: String? = "",
    val village: String? = "",
    val state: String? = "",
    val city: String? = "",
    val pinCode: String? = "",
)
