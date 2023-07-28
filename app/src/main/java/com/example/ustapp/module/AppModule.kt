package com.example.ustapp.module

import android.app.Application
import androidx.room.Room
import com.example.ustapp.dao.MyDatabase
import com.example.ustapp.dao.PostDAO
import com.example.ustapp.repositry.PostRepository
import com.example.ustapp.view.ListActivity
import com.example.ustapp.view.PostActivity
import com.example.ustapp.viewmodel.PostViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// AppModule.kt
@Module
class AppModule(private val application: Application) {
    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideAppDatabase(application: Application): MyDatabase {
        return Room.databaseBuilder(
            application,
            MyDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideUserDao(appDatabase: MyDatabase): PostDAO {
        return appDatabase.postDao()
    }
}
