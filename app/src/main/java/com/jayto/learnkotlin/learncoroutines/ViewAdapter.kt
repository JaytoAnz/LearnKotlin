package com.jayto.learnkotlin.learncoroutines

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jayto.learnkotlin.R
import com.jayto.learnkotlin.model.Users
import kotlinx.android.synthetic.main.list_item.view.*

class ViewAdapter(var list: MutableList<Users>) : RecyclerView.Adapter<ViewAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user : Users = list[position]
        holder.itemView.idName.text = user.idName.toString()
        holder.itemView.Name.text = user.name
    }

}