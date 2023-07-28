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

@Module
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): MyDatabase {
        return Room.databaseBuilder(
            application,
            MyDatabase::class.java, "AppDbb"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: MyDatabase): PostDAO {
        return database.postDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(postDao: PostDAO): PostRepository {
        return PostRepository(postDao)
    }
}

// Create a Component to connect the modules and the classes that need the dependencies
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: ListActivity)
    fun inject(postViewModel: PostViewModel)
    //fun inject(activity: PostActivity)
    // Add more injection methods if needed for other classes
}