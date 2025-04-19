package com.techgeni.walletpage.presentation.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.techgeni.walletpage.presentation.utils.navgraph.MainNavGraph
import com.techgeni.walletpage.presentation.utils.theme.SetSystemBarsColor
import com.techgeni.walletpage.presentation.utils.theme.WalletPageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WalletPageTheme {

                SetSystemBarsColor(
                    statusBarColor = Color.White,
                    navigationBarColor = Color.White,
                    darkIcons = true // qora fon bo‘lsa false bo‘lsin
                )

                Scaffold(modifier = Modifier
                    .fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding).background(Color.White)) {
                        MainNavGraph(navController = rememberNavController())
                    }
                }
            }
        }
    }
}
