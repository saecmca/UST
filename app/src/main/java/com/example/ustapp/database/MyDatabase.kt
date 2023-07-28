package com.example.ustapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ustapp.dao.Post
import com.example.ustapp.dao.PostDAO


@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun userDao(): PostDAO
}