package tn.request.preferences

data class CurrentUser(
    var jwt: String,
    val firstname: String,
    val lastname: String,
    val email: String,
)