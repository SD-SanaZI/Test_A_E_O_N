package com.example.test_a_e_o_n

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.test_a_e_o_n.screens.LoginScreen
import com.example.test_a_e_o_n.screens.PaymentsScreen

sealed class Screens(val route: String) {
    object Login: Screens("login_screen")
    object Payments: Screens("payments_screen")
}

@Composable
fun NavGraph (navController: NavHostController){
    val viewModel: AccountViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route)
    {
        composable(route = Screens.Login.route){
            LoginScreen(navController,viewModel)
        }
        composable(route = Screens.Payments.route){
            PaymentsScreen(navController,viewModel)
        }
    }
}