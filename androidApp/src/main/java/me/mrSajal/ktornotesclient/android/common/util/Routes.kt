package me.mrSajal.ktornotesclient.android.common.util

sealed class Routes(val route:String){
    object Login : Routes("Login")
    object Home : Routes("Home")
    object SignUp : Routes("SignUp")
}