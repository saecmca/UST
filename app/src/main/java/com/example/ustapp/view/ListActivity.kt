package com.example.ustapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ustapp.adapter.PostListAdapter
import com.example.ustapp.dao.Post
import com.example.ustapp.databinding.ActivityListBinding
import com.example.ustapp.module.AppModule
import com.example.ustapp.repositry.DaggerAppComponent
import com.example.ustapp.repositry.ViewModelFactory
import com.example.ustapp.viewmodel.PostViewModel
import javax.inject.Inject
import javax.inject.Provider

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding

    @Inject
    lateinit var viewModelProvider: Provider<PostViewModel>

    private lateinit var viewModelFactory: ViewModelFactory
    private val postViewModel: PostViewModel by viewModels { viewModelFactory }
    lateinit var adapter: PostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnadd.setOnClickListener {
            startActivity(Intent(this, PostActivity::class.java))
        }

        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .build()
        appComponent.inject(this)
        viewModelFactory = ViewModelFactory { viewModelProvider.get() }
        postViewModel.usersLiveData.observe(this, Observer { users ->
            if(users!=null) {
                adapter = PostListAdapter(users as ArrayList<Post>)
                binding.rclview.layoutManager = LinearLayoutManager(this)
                binding.rclview.adapter = adapter
            }
        })

        postViewModel.getUsers()
    }

    override fun onResume() {
        super.onResume()
        postViewModel.getUsers()
    }
}