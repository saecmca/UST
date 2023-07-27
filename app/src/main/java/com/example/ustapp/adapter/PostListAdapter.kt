package com.example.ustapp.adapter

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ustapp.R
import com.example.ustapp.dao.Post
import com.example.ustapp.databinding.ListAdapterBinding
import java.io.File


class PostListAdapter(private val dataList: ArrayList<Post>) :
    RecyclerView.Adapter<PostListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListAdapterBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
        val data=dataList.get(position)
        holder.binding.tvtitle.text=data.title
        holder.binding.tvdate.text=data.date
        holder.binding.tvcaption.text=data.desc
        val file=File(data.image)
        val imgBitmap = BitmapFactory.decodeFile(file.absolutePath)

         holder.binding.imageview.setImageURI(Uri.fromFile(file))
        if(data.flag)
            holder.binding.ivheart.setImageResource(R.drawable.heart_fill)
        else holder.binding.ivheart.setImageResource(R.drawable.heart)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    inner class MyViewHolder
        (val binding: ListAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Post) {
            binding.datas = product
        }
    }
}