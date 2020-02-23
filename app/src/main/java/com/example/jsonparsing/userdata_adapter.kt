package com.example.jsonparsing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class userdata_adapter(private val githubuser:ArrayList<Userdata>):RecyclerView.Adapter<userdata_adapter.UserViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
            UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))

    override fun getItemCount()=githubuser.size

    override fun onBindViewHolder(holder: userdata_adapter.UserViewHolder, position: Int) {
        holder.bind(githubuser[position])

    }
    class UserViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        fun bind(githubuser:Userdata){
            itemView.login.text=githubuser.Login
            itemView.view_url.text=githubuser.html_url
            itemView.score.text= githubuser.score.toString()

        }
    }
}
