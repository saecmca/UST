package com.example.ustapp.dao

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Entity(tableName = "post")
data class Post(
    @PrimaryKey val id: Int,
    val title: String,
    val date: String,
    val image: String,
    val desc: String,
    val flag:Boolean
)

@Database(entities = [Post::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun postDao(): PostDAO
}