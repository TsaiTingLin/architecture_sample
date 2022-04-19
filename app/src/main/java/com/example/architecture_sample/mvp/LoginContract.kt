package com.example.architecture_sample.mvp

import com.example.architecture_sample.model.LoginState

// 建立一份 Contract，用來定義 View 和 Presenter 間互動的介面
class LoginContract {
    interface LoginView {
        fun showLoginState(loginState: LoginState)
    }

    interface LoginPresenter {
        fun viewDestroyed()
        fun login(email: String)
    }
}