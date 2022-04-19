package com.example.architecture_sample.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.architecture_sample.model.LoginRepository
import com.example.architecture_sample.model.LoginState

// ViewModel
class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository()

    // liveData
    private val _loginState = MutableLiveData<LoginState>()
    val loginState:LiveData<LoginState> get() = _loginState

    fun login(account: String) {
        _loginState.value = loginRepository.login(account)
    }
}