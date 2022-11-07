package com.example.gibmejob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gibmejob.model.Routes
import com.example.gibmejob.screens.*
import com.example.gibmejob.screens.login.LoginOptionScreen
import com.example.gibmejob.screens.login.LoginScreen
import com.example.gibmejob.screens.login.RegisterScreen
import com.example.gibmejob.screens.user.UserScreen
import com.example.gibmejob.ui.theme.GibMeJobTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GibMeJobTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GibMeJobApp()
                }
            }
        }
    }
}

@Composable
fun GibMeJobApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen
    ) {
        composable(Routes.SplashScreen) {
            SplashScreen(navController)
        }
        composable(Routes.LoginOptionScreen) {
            LoginOptionScreen(navController)
        }
        composable(Routes.LoginScreen + "/{type}", listOf(navArgument("type") { type = NavType.StringType })) {
            val type = it.arguments!!.getString("type")!!
            LoginScreen(navController = navController, type = type)
        }
        composable(Routes.RegisterScreen) {
            RegisterScreen(navController)
        }

        composable(Routes.UserScreen + "/{type}", listOf(navArgument("type") { type = NavType.StringType })) {
            val type = it.arguments!!.getString("type")!!
            UserScreen(navController = navController, type = type)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GibMeJobTheme {
        GibMeJobApp()
    }
}