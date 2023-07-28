package com.example.ustapp.repositry

import com.example.ustapp.module.AppModule
import com.example.ustapp.view.ListActivity
import com.example.ustapp.view.PostActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: ListActivity)
    fun inject(activity: PostActivity)
}