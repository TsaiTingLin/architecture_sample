package com.example.architecture_sample.mvp

import android.view.View
import com.example.architecture_sample.model.LoginRepository

class LoginPresenterImpl(private var view: LoginContract.LoginView?) :
    LoginContract.LoginPresenter {
    private val loginRepository = LoginRepository()

    override fun viewDestroyed() {
        view = null
    }

    override fun login(email: String) {
        val data = loginRepository.login(email)
        view?.showLoginState(data)
    }
}