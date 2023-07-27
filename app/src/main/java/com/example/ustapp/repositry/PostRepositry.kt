package com.example.ustapp.repositry

import com.example.ustapp.dao.Post
import com.example.ustapp.dao.PostDAO

class PostRepository(private val postDao: PostDAO) {
    suspend fun getUsers(): ArrayList<Post> {
        return postDao.getUsers()
    }

    suspend fun insertUser(user: Post) {
        postDao.insertUser(user)
    }
}