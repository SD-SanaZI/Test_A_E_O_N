package com.example.test_a_e_o_n

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val login = mutableStateOf("")
    val password = mutableStateOf("")
    val errorMSG = mutableStateOf("")
    val toPayments = mutableStateOf(false)

    fun next(accountViewModel:AccountViewModel){
        val handlerException = CoroutineExceptionHandler{
                _, throwable ->
            Log.d("Login exception", "exception handled: ${throwable.message}")
        }
        val scope = CoroutineScope(Dispatchers.IO + handlerException)
        scope.launch {
            val result: LoginResult = NetworkModule().service.login(
                "12345",
                "1",
                UserInfo(login.value, password.value)
            )
            errorMSG.value = result.error.errorMsg
            if (result.success == "true") {
                toPayments.value = true
                accountViewModel.login = login.value
                accountViewModel.token = result.response.token
            }
        }
    }
}