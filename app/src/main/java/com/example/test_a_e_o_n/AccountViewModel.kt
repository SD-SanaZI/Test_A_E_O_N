package com.example.test_a_e_o_n

import androidx.lifecycle.ViewModel

class AccountViewModel : ViewModel() {
    var login = ""
    var token = ""

    fun reset(){
        login = ""
        token = ""
    }
}