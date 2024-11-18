package com.example.myjetpackapplication

import com.example.presentation.charviewmodels.ViewModelDiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelDiModule::class])
interface AppComponent {
    fun inject(application: com.example.myjetpackapplication.RickAndMorty)

    fun inject(activity: MainActivity)
}
