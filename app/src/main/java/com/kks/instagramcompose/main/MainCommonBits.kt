package com.kks.instagramcompose.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.kks.instagramcompose.DestinationScreen
import com.kks.instagramcompose.InstagramViewModel
import com.kks.instagramcompose.ui.theme.SkyBlue700

@Composable
fun NotificationMessage(vm: InstagramViewModel) {
    val notificationState = vm.popupNotification.value
    val notificationMessage = notificationState?.getContentOrNull()
    if (notificationMessage != null) {
        Toast.makeText(LocalContext.current, notificationMessage, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun CommonProgressSpinner() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.Gray)
            .clickable(enabled = false) {
            }
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically        
    ) {
//        LinearProgressIndicator()
        CircularProgressIndicator()
    }
}

fun navigateTo(navController: NavController, destinationScreen: DestinationScreen){
    navController.navigate(destinationScreen.route){
        popUpTo(destinationScreen.route)
        launchSingleTop = true
    }
}