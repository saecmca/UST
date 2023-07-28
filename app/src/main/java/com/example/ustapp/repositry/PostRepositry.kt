package com.example.ustapp.repositry

import com.example.ustapp.dao.Post
import com.example.ustapp.dao.PostDAO
import javax.inject.Inject

class PostRepository @Inject constructor(private val postDao: PostDAO) {
    suspend fun getUsers(): List<Post> {
        return postDao.getUsers()
    }

    suspend fun insertUser(user: Post) {
        postDao.insertUser(user)
    }
}