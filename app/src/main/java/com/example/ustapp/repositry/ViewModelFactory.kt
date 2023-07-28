package com.example.ustapp.repositry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ustapp.viewmodel.PostViewModel
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val viewModelProvider: () -> PostViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider() as T
    }
}