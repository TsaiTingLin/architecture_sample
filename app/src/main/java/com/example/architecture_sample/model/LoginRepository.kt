package com.example.architecture_sample.model

class LoginRepositoryImpl {
    fun login(email: String, password: String): LoginState {
        // do login
        return LoginState.Login(email)
    }
}