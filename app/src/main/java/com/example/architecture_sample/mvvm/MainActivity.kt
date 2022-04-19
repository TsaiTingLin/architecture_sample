package com.example.architecture_sample.mvvm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.architecture_sample.databinding.ActivityMainBinding
import com.example.architecture_sample.model.LoginState
import com.example.architecture_sample.mvp.MvpActivity

// View
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 點擊登入鍵
        binding.loginButton.setOnClickListener {
            // 取得輸入值
            val inputText = binding.inputEditText.text.toString()

            // 收鍵盤
            hideKeyboard()

            // 清空輸入
            binding.inputEditText.text.clear()
            Log.d("Test_login", "$inputText")

            // 處理登入
            loginViewModel.login(inputText)

        }

        // 監聽登入狀態
        loginViewModel.loginState.observe(this) { loginState ->
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

        // 前往MVP方式登入頁面
        binding.changeButton.setOnClickListener {
            goToMVPPage()
        }
    }

    override fun onPause() {
        super.onPause()
        // 清空帳號顯示
        binding.loginAccountTextView.text = ""
    }

    // 收鍵盤
    private fun hideKeyboard() {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(
            binding.inputEditText.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN
        )
    }

    private fun goToMVPPage() {
        startActivity(Intent(this, MvpActivity::class.java))
    }
}