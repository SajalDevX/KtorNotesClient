package me.mrSajal.ktornotesclient.di

import me.mrSajal.ktornotesclient.auth.data.AuthRepositoryImpl
import me.mrSajal.ktornotesclient.auth.data.AuthService
import me.mrSajal.ktornotesclient.auth.domain.repository.AuthRepository
import me.mrSajal.ktornotesclient.auth.domain.usecase.SignInUseCase
import me.mrSajal.ktornotesclient.auth.domain.usecase.SignUpUseCase
import me.mrSajal.ktornotesclient.common.util.provideDispatcher
import org.koin.dsl.module


private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

fun getSharedModules() = listOf(authModule, utilityModule, platformModule)