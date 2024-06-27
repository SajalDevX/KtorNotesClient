package me.mrSajal.ktornotesclient.android.di

import me.mrSajal.ktornotesclient.android.MainActivityViewModel
import me.mrSajal.ktornotesclient.android.auth.login.LoginViewModel
import me.mrSajal.ktornotesclient.android.auth.signup.SignUpViewModel
import me.mrSajal.ktornotesclient.android.home.HomeScreenViewModel
import me.mrSajal.ktornotesclient.android.notes.NotesScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel {SignUpViewModel(get())}
    viewModel { MainActivityViewModel(get()) }
    viewModel { NotesScreenViewModel(get(),get(),get(),get()) }
    viewModel { HomeScreenViewModel(get(),get()) }

}