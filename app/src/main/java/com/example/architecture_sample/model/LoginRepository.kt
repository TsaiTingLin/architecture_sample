package com.example.architecture_sample.model

class LoginRepository {
    fun login(email: String): LoginState {
        // 模擬打api取登入狀態
        return if (email.contains("tina")) {
            LoginState.Success(email)
        } else {
            LoginState.Fail
        }
    }
}