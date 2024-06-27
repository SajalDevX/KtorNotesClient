package me.mrSajal.ktornotesclient.android.common.util

sealed class Routes(val route:String){
    object Login : Routes("Login")
    object Home : Routes("Home")
    object SignUp : Routes("SignUp")
    object Notes : Routes("Notes"){
        const val ARG_NOTE_ID = "noteId"
        fun withNoteId(noteId: String) = "Notes/$noteId"
    }
}