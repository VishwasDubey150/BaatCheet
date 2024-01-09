package com.example.baat_cheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baat_cheet.screens.LoginScreen
import com.example.baat_cheet.screens.SignUpScreen
import com.example.baat_cheet.ui.theme.BaatcheetTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class DestinationScreen(var route :String)
{
    object  SignUp : DestinationScreen("signup")
    object  Login : DestinationScreen("login")
    object  Profile : DestinationScreen("profile")
    object  ChatList : DestinationScreen("chatList")
    object  SingleChat : DestinationScreen("singleChat/{chatId}"){
        fun createRoute(id: String)="singlechat/$id"
    }
    object  StatusList : DestinationScreen("statusList")
    object  SingleStatus : DestinationScreen("singleStatus/{userId}"){
        fun createRoute(userid: String)="singlechat/$userid"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaatcheetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatAppNavigation()
                }
            }
        }
    }

    @Composable
    fun ChatAppNavigation()
    {

        val navController = rememberNavController()
        var vm=hiltViewModel<BCViewModel>()

        NavHost(navController = navController , startDestination = DestinationScreen.SignUp.route)
        {
            composable(DestinationScreen.SignUp.route)
            {
                SignUpScreen(navController,vm)
            }
            composable(DestinationScreen.Login.route)
            {
                LoginScreen(navController)
            }
        }
    }
}

