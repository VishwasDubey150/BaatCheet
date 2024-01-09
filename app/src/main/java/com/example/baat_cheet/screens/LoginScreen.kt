package com.example.baat_cheet.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.baat_cheet.DestinationScreen

@Composable
fun LoginScreen( navController: NavController) {
    Text(text = "This is login NAvigate to other Screen",
        modifier = Modifier.clickable {
            navController.navigate(DestinationScreen.SignUp.route)
        })
}