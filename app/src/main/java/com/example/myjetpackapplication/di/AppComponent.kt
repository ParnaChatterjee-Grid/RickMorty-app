package com.example.myjetpackapplication.di



import com.example.myjetpackapplication.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
               com.example.data.di.ApolloClient::class, com.example.data.module.DataModule::class
    ]
)

interface AppComponent {
    fun inject(application: com.example.myjetpackapplication.BaseApplication)
    fun inject(activity: MainActivity)
}
