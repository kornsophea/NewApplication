package com.example.newapplication.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.newapplication.DataModel.NewsDataModel
import com.example.newapplication.NewsDetail
import com.example.newapplication.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class NewsAdapter(var list:ArrayList<NewsDataModel>,private val context: Context):RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private lateinit var db:FirebaseFirestore
    private lateinit var storage: StorageReference
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var imageNews:ImageView=itemView.findViewById(R.id.img_news) as ImageView
        var txtTitle:TextView=itemView.findViewById(R.id.txt_title) as TextView
        var txtIntroduction:TextView=itemView.findViewById(R.id.txt_introduction) as TextView
        var txtAuthor:TextView=itemView.findViewById(R.id.txt_author) as TextView
        var txtDate:TextView=itemView.findViewById(R.id.txt_date) as TextView
        val cardNews = itemView.findViewById(R.id.card_news) as LinearLayout
        val btnDelete = itemView.findViewById(R.id.btn_delete) as ImageView



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_news,parent,false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel:NewsDataModel=list[position]
        holder.txtTitle.text=dataModel.title
        holder.txtIntroduction.text=dataModel.introduction
        holder.txtAuthor.text=dataModel.author
        holder.txtDate.text=dataModel.date
        Log.d("",list[position].content)

        Picasso.get().load(list[position].imageUrl).into(holder.imageNews)
        holder.cardNews.setOnClickListener {
            val intent = Intent(context,NewsDetail::class.java)
            intent.putExtra("imageUrl",list[position].imageUrl)
            intent.putExtra("content",list[position].content)
            intent.putExtra("id",list[position].id)
            context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {

            AlertDialog.Builder(context)
                .setTitle("Delete")
                .setIcon(R.drawable.ic_baseline_delete_24)
                .setMessage("Are you sure ?")
                .setPositiveButton("yes"
                ) { dialogInterface: DialogInterface, i: Int ->

                        notifyItemRemoved(position)
                        deleteDataFireStore(list[position].id)
                        list.removeAt(position)
                        notifyDataSetChanged()
                        dialogInterface.dismiss()
                }
                .setNegativeButton("no"){ dialogInterface: DialogInterface, i: Int ->

                        dialogInterface.dismiss()

                }.create().show()
        }
    }

    override fun getItemCount():Int =  list.size

    private fun deleteDataFireStore(id:String){
        db = FirebaseFirestore.getInstance()
        db.collection("news").document(id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {

            }

        storage = FirebaseStorage.getInstance().getReference("image")
        storage.child(id).delete()


    }

}