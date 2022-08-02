package com.example.newapplication.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.newapplication.DataModel.User
import com.example.newapplication.MainActivity
import com.example.newapplication.databinding.ActivitySigninScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SigninScreen : AppCompatActivity() {
    private lateinit var binding:ActivitySigninScreenBinding
    private lateinit var auth:FirebaseAuth
    
    private lateinit var db:FirebaseFirestore
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=Firebase.auth
        db=FirebaseFirestore.getInstance()

        binding.apply {
            btnSigIn.setOnClickListener{

                var email=edtEmail.text.toString()
                var password=edtPassword.text.toString()
                createUserAccount(email,password)
            }
        }
    }
    private fun createUserAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Authentication Successfully.",
                        Toast.LENGTH_SHORT).show()
                    storeUserAccount()
                    startActivity(Intent(this,MainActivity::class.java))
                } else {

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun storeUserAccount(){

        val data=User(
            auth.uid.toString(),
            binding.edtName.text.toString(),
            binding.edtEmail.text.toString(),

        )
        db.collection("Users").document(auth.uid.toString()).set(data)
    }

}


