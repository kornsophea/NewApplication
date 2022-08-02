package com.example.newapplication.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.newapplication.DataModel.NewsDataModel
import com.example.newapplication.R
import com.example.newapplication.databinding.FragmentAddBinding
import com.example.newapplication.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class AddFragment : Fragment()
//    DatePickerDialog.OnDateSetListener
{


    private lateinit var binding: FragmentAddBinding
    private lateinit var db :FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private val PICK_IMAGE =0
    private var uri : Uri? =null
    private lateinit var calender:Calendar
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var date:String
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth=FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding = FragmentAddBinding.inflate(layoutInflater)

        binding.btnAdd.setOnClickListener {
         if(binding.editTitle.text.toString()==""|| binding.editAuthor.text.toString()==""||binding.editIntroduction.text.toString()==""||binding.editContent.text.toString()==""||binding.imageshow.equals("")){
             Toast.makeText(context,"Please input information", Toast.LENGTH_SHORT).show()
         }else{
             upload()
         }

        }

//        binding.editCurrantDate.setOnClickListener {
//            chooseDate()
//        }
        calender=Calendar.getInstance()
        simpleDateFormat=SimpleDateFormat("dd-MM-yy HH:mm:ss")
        date=simpleDateFormat.format(calender.time)

        binding.btnAddImage.setOnClickListener{
            val intent  = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent,PICK_IMAGE)
        }
        // Inflate the layout for this fragment
        return binding.root
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK){
            uri = data?.data
            binding.imageshow.setImageURI(data?.data)
        }
    }
    private fun nullValue(){
        binding.imageshow.setImageURI(null)
        binding.editTitle.text = null
        binding.editAuthor.text=null
        binding.editIntroduction.text=null
        binding.editContent.text=null
        date
    }


//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun chooseDate(){
//       val date = DatePickerDialog(
//           requireContext(),
//           this,
//           Calendar.getInstance().get(Calendar.YEAR),
//           Calendar.getInstance().get(Calendar.YEAR),
//           Calendar.getInstance().get(Calendar.YEAR)
//
//       )
//
//        date.show()
//    }

    // upload new
    private fun upload(){
        var id = idNew()
        db = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance().getReference("image")

        storage.child(id).putFile(uri!!).addOnCompleteListener {
            if (it.isSuccessful){

                storage.child(id).downloadUrl.addOnSuccessListener{link->
                    Log.d("URL IMAGE ",link.toString())

                    val NewsDataModel = NewsDataModel(
                        id,
                        auth.uid.toString(),
                        link.toString(),
                        binding.editTitle.text.toString(),
                        binding.editAuthor.text.toString(),
                        binding.editIntroduction.text.toString(),
                        binding.editContent.text.toString(),
//                        binding.editCurrantDate.text.toString()
                        date
                    )
                    db.collection("news").document(id).set(NewsDataModel)
                    Toast.makeText(context,"Successfully", Toast.LENGTH_SHORT).show()

                }
            }else{
                Toast.makeText(context,"Fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun idNew() :String{
        return System.currentTimeMillis().toString()
    }


//    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
//        binding.editCurrantDate.setText("$year - ${month+1} - $day")
//    }
//    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
//        binding.editCurrantDate.setText(date)
//    }


}