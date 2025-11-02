package com.example.aulatelas.screens.telasscreens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.aulatelas.ui.theme.green

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    Scaffold(
        floatingActionButton = {
            Example(
                onClick = { Log.d("FAB", "Clicked") }
            )
        }
    ) { padding ->
        Box(
            modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            Text(
                "Tela Principal",
                Modifier.align(Alignment.Center),
                style = TextStyle.Default.copy(
                    fontSize = 32.sp
                )
            )
        }
    }
}

@Composable
fun Example(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = green,
        contentColor = Color.White,


        ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}