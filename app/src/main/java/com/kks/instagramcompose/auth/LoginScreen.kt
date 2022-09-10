package com.kks.instagramcompose.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kks.instagramcompose.DestinationScreen
import com.kks.instagramcompose.InstagramViewModel
import com.kks.instagramcompose.main.navigateTo
import com.kks.instagramcompose.ui.theme.SkyBlue700

@Composable
fun LoginScreen(navController: NavController, vm: InstagramViewModel){
    Text(text = "처음 오셨다면 회원가입을 해주세요 :)",
        color = SkyBlue700,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navigateTo(navController, DestinationScreen.Signup)
            }
    )
}