package com.example.architecture_sample.mvvm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.architecture_sample.R
import com.example.architecture_sample.model.LoginState
import com.example.architecture_sample.mvp.MvpActivity

class FindViewByIdActivity : AppCompatActivity() {

    private lateinit var inputEditText: EditText
    private lateinit var loginAccountTextView: TextView
    private lateinit var loginButton: Button
    private lateinit var changeButton:Button

    private val loginViewModel by viewModels<LoginViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initObserver()

        // 預設顯示鍵盤
        showKeyboard(inputEditText)
    }

    override fun onPause() {
        super.onPause()
        // 清空帳號顯示
        loginAccountTextView.text = ""
    }

    private fun initView() {
        // findViewById
        inputEditText = findViewById(R.id.input_editText)
        loginAccountTextView = findViewById(R.id.login_account_textView)
        loginButton = findViewById(R.id.login_button)
        changeButton = findViewById(R.id.change_button)

        // 點擊登入鍵
        loginButton.setOnClickListener {
            // 取得輸入值
            val inputText = inputEditText.text.toString()

            // 收鍵盤
            hideKeyboard()

            // 清空輸入
            inputEditText.text.clear()
            loginAccountTextView.text = ""

            // 處理登入
            loginViewModel.login(inputText)
        }

        // 前往MVP方式登入頁面
        changeButton.setOnClickListener {
            goToMVPPage()
        }
    }

    private fun initObserver() {
        // 監聽登入狀態
        loginViewModel.loginState.observe(this) { loginState ->
            when (loginState) {
                is LoginState.Success -> {
                    // 顯示登入帳號
                    loginAccountTextView.text = "歡迎 ${loginState.loginEmail} 回來"
                    Toast.makeText(this, "登入成功", Toast.LENGTH_SHORT).show()
                }
                is LoginState.Fail -> {
                    Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 收鍵盤
    private fun hideKeyboard() {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(
            inputEditText.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    // 顯示鍵盤
    private fun showKeyboard(editText: EditText) {
        editText.postDelayed(
            {
                editText.requestFocus()
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.showSoftInput(
                    inputEditText,
                    InputMethodManager.SHOW_IMPLICIT
                )
            }, 500
        )
    }

    private fun goToMVPPage() {
        startActivity(Intent(this, MvpActivity::class.java))
    }
}