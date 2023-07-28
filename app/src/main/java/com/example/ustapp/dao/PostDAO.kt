package com.example.ustapp.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDAO {
    @Query("SELECT * FROM post")
    suspend fun getUsers(): List<Post>

    @Insert
    suspend fun insertUser(user: Post)
}