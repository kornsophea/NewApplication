package com.example.newapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newapplication.DataModel.User
import com.example.newapplication.R

class APIAdapter(val context: Context,private var list:List<User>):RecyclerView.Adapter<APIAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvID = itemView.findViewById<TextView>(R.id.text_id)
        val tvTitle = itemView.findViewById<TextView>(R.id.text_title)
        val tvbody = itemView.findViewById<TextView>(R.id.text_body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_api,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvID.text = list[position].id.toString()
        holder.tvTitle.text=list[position].name
        holder.tvbody.text=list[position].email

    }

    override fun getItemCount(): Int {
       return  list.size
    }
}