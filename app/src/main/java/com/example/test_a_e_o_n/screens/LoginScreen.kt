package com.example.test_a_e_o_n.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.test_a_e_o_n.AccountViewModel
import com.example.test_a_e_o_n.LoginViewModel
import com.example.test_a_e_o_n.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, accountViewModel: AccountViewModel) {
    //Переход на страницу Payment
    val loginViewModel: LoginViewModel = viewModel()
    if(loginViewModel.toPayments.value){
        loginViewModel.toPayments.value = false
        navController.navigate(Screens.Payments.route)
    }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
    ){
        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Логин",
                fontSize = 16.sp,
            )
            TextField(
                value = loginViewModel.login.value,
                onValueChange =
                {
                        newText ->
                    if(newText.all { it.isLetterOrDigit()})
                        loginViewModel.login.value = newText
                    else
                        loginViewModel.errorMSG.value = "Логин может содержать только символы a-z,A-Z или 0-9"
                },
                Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(size = 20.dp))
                    .border(width = 2.dp, Color.Black, RoundedCornerShape(size = 20.dp))
            )
            Text(
                text = "Пароль",
                Modifier
                    .padding(top = 16.dp),
                fontSize = 16.sp
            )
            TextField(
                value = loginViewModel.password.value,
                onValueChange =
                {
                        newText ->
                    if(newText.all { it.isLetterOrDigit()})
                        loginViewModel.password.value = newText
                    else
                        loginViewModel.errorMSG.value = "Пароль может содержать только символы a-z,A-Z или 0-9"
                },
                Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(size = 20.dp))
                    .border(width = 2.dp, Color.Black, RoundedCornerShape(size = 20.dp)),
                visualTransformation ={text ->
                    var newText = ""
                    text.text.forEach { _ ->
                        newText += "*"
                    }
                    TransformedText(AnnotatedString(newText), OffsetMapping.Identity)
                }
            )
            Text(
                text = loginViewModel.errorMSG.value,
                Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .heightIn(44.dp,120.dp),
                color = Color.Red,
                fontSize = 16.sp
            )
        }
        Box(modifier = Modifier
            .padding(16.dp)
            .height(44.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 20.dp))
            .background(Color(0xFFEAEEF4))
            .clickable(
                onClick = {
                    loginViewModel.next(accountViewModel)
                }
            ),
            contentAlignment = Alignment.Center
        ){
            Text(
                "Вход",
                textAlign = TextAlign.Center
            )
        }
    }
}