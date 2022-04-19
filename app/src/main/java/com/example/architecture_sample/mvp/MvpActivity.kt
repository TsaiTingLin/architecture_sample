package com.example.architecture_sample.mvp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.architecture_sample.databinding.ActivityMainBinding
import com.example.architecture_sample.model.LoginState

// view要做兩件事
// 通知 Presenter 有關於 View 的生命週期，或是使用者的輸入。
// 實現 Contract 的 View 介面，來顯示資料。
class MvpActivity : AppCompatActivity(), LoginContract.LoginView {

    private lateinit var binding: ActivityMainBinding
    private val loginPresenter: LoginContract.LoginPresenter by lazy {
        LoginPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 點擊登入鍵
        binding.loginButton.apply {
            text = "MVP登入"
            setOnClickListener {
                // 取得輸入值
                val inputText = binding.inputEditText.text.toString()

                // 收鍵盤
                hideKeyboard()

                // 清空輸入
                binding.inputEditText.text.clear()
                Log.d("Test_login", "$inputText")

                // 處理登入
                loginPresenter.login(inputText)
            }
        }

        // 返回
        binding.changeButton.apply {
            text = "返回MVVM登入"
            setOnClickListener {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.viewDestroyed()
    }

    override fun showLoginState(loginState: LoginState) {
        when (loginState) {
            is LoginState.Success -> {
                // 顯示登入帳號
                binding.loginAccountTextView.text = "歡迎 ${loginState.loginEmail} 回來"
                Toast.makeText(this, "登入成功", Toast.LENGTH_SHORT).show()
            }
            is LoginState.Fail -> {
                Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 收鍵盤
    private fun hideKeyboard() {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(
            binding.inputEditText.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN
        )
    }
}