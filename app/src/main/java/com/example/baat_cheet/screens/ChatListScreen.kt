package com.example.baat_cheet.screens


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.baat_cheet.BCViewModel

@Composable
fun  ChatListScreen(navController: NavController,vm: BCViewModel) {
    Text(text = "chatlist")
    
    BottomNavigationMenu(selectedItem = BottomNavigationItem.CHATLIST, navController =navController )

}