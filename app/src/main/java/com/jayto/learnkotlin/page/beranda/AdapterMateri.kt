package com.jayto.learnkotlin.page.beranda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jayto.learnkotlin.R
import com.jayto.learnkotlin.model.Materi
import kotlinx.android.synthetic.main.list_item.view.*




class AdapterMateri(private val listener: AdapterListener) : RecyclerView.Adapter<AdapterMateri.Holder>(){

    var list : ArrayList<Materi> = ArrayList()

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    fun addItem(list: ArrayList<Materi>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun getData() : ArrayList<Materi>{
        return this.list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val materi = list[position]
        holder.view.idName.text = materi.getid
        holder.view.Name.text = materi.nameurl

        holder.view.setOnClickListener {
            listener.clickListener(list[position])
        }
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, list.size)
    }

    interface AdapterListener{
        fun clickListener(materi: Materi)
//        fun deleteListener(materi: Materi, position: Int)
    }
}