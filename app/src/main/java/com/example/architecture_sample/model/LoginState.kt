package com.example.architecture_sample.model

sealed class LoginState {
    class Success(val loginEmail:String): LoginState()
    object Fail: LoginState()
}