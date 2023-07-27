package com.example.ustapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.ustapp.adapter.PostListAdapter
import com.example.ustapp.dao.Post
import com.example.ustapp.databinding.ActivityListBinding
import com.example.ustapp.module.AppModule
import com.example.ustapp.module.DaggerAppComponent
import com.example.ustapp.viewmodel.PostViewModel

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
   // @Inject
    lateinit var postViewModel: PostViewModel
    lateinit var adapter:PostListAdapter
    val mainViewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnadd.setOnClickListener {
            startActivity(Intent(this, PostActivity::class.java))
        }

        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule)
            .build()
        appComponent.inject(this@ListActivity)

         mainViewModel.usersLiveData.observe(this, Observer { users ->

             adapter = PostListAdapter(users)
             binding.rclview.layoutManager = LinearLayoutManager(this)
             binding.rclview.adapter = adapter
         })

        postViewModel.getUsers()
    }
}