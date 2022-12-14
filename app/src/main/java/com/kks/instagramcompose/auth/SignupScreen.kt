package com.kks.instagramcompose.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kks.instagramcompose.DestinationScreen
import com.kks.instagramcompose.InstagramViewModel
import com.kks.instagramcompose.R
import com.kks.instagramcompose.main.CommonProgressSpinner
import com.kks.instagramcompose.main.navigateTo
import com.kks.instagramcompose.ui.theme.SkyBlue500
import com.kks.instagramcompose.ui.theme.SkyBlue700

@Composable
fun SignupScreen(navController: NavController, vm: InstagramViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            val usernameState = remember { mutableStateOf(TextFieldValue())}
            val emailState = remember { mutableStateOf(TextFieldValue())}
            val passState = remember { mutableStateOf(TextFieldValue())}

            Image(
                painter = painterResource(id = R.drawable.instagram_logo),
                contentDescription = "Instagram Compose",
                modifier = Modifier
                    .width(300.dp)
                    .padding(top = 75.dp)
                    .padding(8.dp)
            )

            Text(
                text = "??????????????? ????????????",
                modifier = Modifier.padding(20.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif
            )
            OutlinedTextField(
                value = usernameState.value,
                onValueChange = {usernameState.value = it},
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "??????")}
            )
            OutlinedTextField(
                value = emailState.value,
                onValueChange = {emailState.value = it},
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "?????????")}
            )
            OutlinedTextField(
                value = passState.value,
                onValueChange = {passState.value = it},
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "????????????")},
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
                          vm.onSignup(
                              usernameState.value.text,
                              emailState.value.text,
                              passState.value.text
                          )
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "????????????")
            }
            Text(text = "?????? ?????????????????? ???????????? ???????????? :)",
                 color = SkyBlue700,
                 modifier = Modifier
                     .padding(8.dp)
                     .clickable {
                         navigateTo(navController, DestinationScreen.Login)
                     }
            )
        }

        val isLoading = vm.isProgress.value
        if (isLoading) {
            CommonProgressSpinner()
        }
    }
}