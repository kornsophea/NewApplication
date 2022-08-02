package com.example.newapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newapplication.Adapter.APIAdapter
import com.example.newapplication.Adapter.NewsAdapter
import com.example.newapplication.DataModel.NewsDataModel
import com.example.newapplication.DataModel.User
import com.example.newapplication.databinding.FragmentHomeBinding
import com.example.newapplication.screens.APIInterface
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class HomeFragment() : Fragment() {

    private lateinit var db:FirebaseFirestore
    private lateinit var newArrayList:ArrayList<NewsDataModel>
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentHomeBinding.inflate(layoutInflater)

        newArrayList= ArrayList<NewsDataModel>()


        ReadData()



        return binding.root

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
////        getApi()
//    }

//    override fun onResume() {
//        super.onResume()
//        newArrayList.clear()
//        ReadData()
//    }
    private fun ReadData(){
        db= FirebaseFirestore.getInstance()
        db.collection("news")
            .get()
            .addOnSuccessListener{result->
                for (document in result){
                    newArrayList.add(
                        NewsDataModel(
                            document.data["id"].toString(),
                            document.data["userid"].toString(),
                            document.data["imageUrl"].toString(),
                            document.data["title"].toString(),
                            document.data["author"].toString(),
                            document.data["introduction"].toString(),
                            document.data["content"].toString(),
                            document.data["date"].toString(),
                        )
                    )


                }
                newArrayList.sortBy { it.title }
                binding.listNews.layoutManager= LinearLayoutManager(context)
                binding.listNews.adapter = context?.let { NewsAdapter(newArrayList, it) }
                binding.listNews.setHasFixedSize(true)

            }

    }

    private fun getApi(){
        val baseURL ="http://localhost:3000/"
        val retrofit = Retrofit
                     .Builder()
                     .baseUrl(baseURL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .build()
                     .create(APIInterface::class.java)

        val data = retrofit.getUser()

        data.enqueue(object :Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful)
                {
                    Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
                    println("testapi"+response.body())
//                    var list:List<User> = response.body()!!
//                    binding.apiRecyclerview.layoutManager = LinearLayoutManager(requireContext())
//                    binding.apiRecyclerview.adapter = APIAdapter(requireContext(),list)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("Error ",t.message.toString())
                Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()

            }

        })






    }


}