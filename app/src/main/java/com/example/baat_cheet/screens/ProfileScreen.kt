package com.example.baat_cheet.screens


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.baat_cheet.BCViewModel

@Composable
fun  ProfileScreen(navController: NavController, vm: BCViewModel) {


    Text(text = "Profile")

    BottomNavigationMenu(selectedItem = BottomNavigationItem.PROFILE, navController =navController )
}