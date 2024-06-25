package me.mrSajal.ktornotesclient.android.di

import me.mrSajal.ktornotesclient.android.MainActivityViewModel
import me.mrSajal.ktornotesclient.android.auth.login.LoginViewModel
import me.mrSajal.ktornotesclient.android.auth.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel {SignUpViewModel(get())}
    viewModel { MainActivityViewModel(get()) }
}