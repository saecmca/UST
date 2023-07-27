package com.example.ustapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ustapp.dao.Post
import com.example.ustapp.repositry.PostRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {

    private val _usersLiveData = MutableLiveData<ArrayList<Post>>()
    val usersLiveData: LiveData<ArrayList<Post>> = _usersLiveData

    fun getUsers() {
        viewModelScope.launch {
            val users = postRepository.getUsers()
            _usersLiveData.postValue(users)
        }
    }

    fun insertUser(user: Post) {
        viewModelScope.launch {
            postRepository.insertUser(user)
            getUsers()
        }
    }
}
