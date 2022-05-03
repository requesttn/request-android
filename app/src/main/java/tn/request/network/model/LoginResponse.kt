package tn.request.network.model

data class LoginResponse(
    val jwt: String,
    val user: UserResponse
)