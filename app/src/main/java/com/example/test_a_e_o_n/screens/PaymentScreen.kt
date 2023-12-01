package com.example.test_a_e_o_n.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.test_a_e_o_n.AccountViewModel
import com.example.test_a_e_o_n.NetworkModule
import com.example.test_a_e_o_n.Payment
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PaymentsScreen(navController: NavController, viewModel: AccountViewModel) {
    val payments = remember { mutableStateOf(listOf<Payment>()) }
    val handlerException = CoroutineExceptionHandler{
            _, throwable ->
        Log.d("Login exception", "exception handled: ${throwable.message}")
    }
    val scope = CoroutineScope(Dispatchers.IO + handlerException)
    LaunchedEffect(Unit,scope){
        payments.value = withContext(Dispatchers.IO){
            NetworkModule().service.payments(
                "12345",
                "1",
                viewModel.token
            ).response
        }
    }
    Column(
        Modifier.padding(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row (
                Modifier.weight(1f)
            ){
                Text(
                    text = "Пользователь:",
                    fontSize = 16.sp
                    )
                Text(
                    text = viewModel.login,
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Box(modifier = Modifier
                .height(44.dp)
                .clip(RoundedCornerShape(size = 8.dp))
                .background(Color(0xFFEAEEF4))
                .clickable(
                    onClick = {
                        viewModel.reset()
                        navController.navigateUp()
                    }
                )
                .padding(16.dp,8.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    "Выйти",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            payments.value.forEach{
                Box(
                    Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(RoundedCornerShape(size = 20.dp))
                        .background(Color(0xFFEAEEF4)),
                    Alignment.Center
                ){
                    Text(
                        text = it.title,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}