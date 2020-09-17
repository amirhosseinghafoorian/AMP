package com.a.amp.user.apimodel1

data class LoginResponse(
    val user: User,
    val errors: Errors
)