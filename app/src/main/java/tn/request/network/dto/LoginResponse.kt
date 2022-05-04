package tn.request.network.dto

import tn.request.model.User

data class LoginResponse(
    val jwt: String,
    val user: User
)