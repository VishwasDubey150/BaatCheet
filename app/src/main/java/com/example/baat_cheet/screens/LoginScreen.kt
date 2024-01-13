package com.example.baat_cheet.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baat_cheet.BCViewModel
import com.example.baat_cheet.DestinationScreen
import com.example.baat_cheet.PB
import com.example.baat_cheet.R
import com.example.baat_cheet.checkSignedIn
import com.example.baat_cheet.navigateTo

@Composable
fun LoginScreen( navController: NavController,vm:BCViewModel) {

    checkSignedIn(vm = vm, navcontroller = navController)
    Box(modifier = Modifier.fillMaxSize())
    {
        Column(modifier = Modifier

            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .wrapContentHeight()
            , horizontalAlignment = Alignment.CenterHorizontally)
        {

            val emailState= remember {
                mutableStateOf(TextFieldValue())
            }
            val passwordState= remember {
                mutableStateOf(TextFieldValue())
            }

            Image(painter = painterResource(id = R.drawable.icon)
                , contentDescription =null, modifier = Modifier
                    .width(200.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp))

            Text(text = "Log in",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )


            OutlinedTextField(value = emailState.value,
                onValueChange ={
                    emailState.value=it
                },
                label = {Text(text = "email")},
                modifier = Modifier.padding(8.dp))

            OutlinedTextField(value = passwordState.value,
                onValueChange ={
                    passwordState.value=it
                },
                label = {Text(text = "password")},
                modifier = Modifier.padding(8.dp))

            Button(onClick = {
                vm.Login(
                    emailState.value.text,
                    passwordState.value.text)},
            modifier= Modifier.padding(8.dp)){
                Text(text = "Log in")
            }
            Text(text = "Create account", color = Color.Gray,
                fontSize = 12.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navigateTo(navController, DestinationScreen.SignUp.route) }
            )
        }
    }
    if(vm.inProgress.value==true)
    {
        PB()
    }

}