package com.example.architecture_sample.model

sealed class LoginState {
    class Login(val loginEmail:String): LoginState()
    object Logout: LoginState()
}