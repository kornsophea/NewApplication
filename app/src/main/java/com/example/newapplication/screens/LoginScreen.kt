package com.example.newapplication.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.newapplication.MainActivity
import com.example.newapplication.R
import com.example.newapplication.databinding.ActivityLoginBinding
import com.example.newapplication.databinding.ActivityLoginScreenBinding
import com.example.newapplication.databinding.ActivityNewsDetailBinding
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {
    private lateinit var binding: ActivityLoginScreenBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        binding.apply {
            btnLogin.setOnClickListener {
                var email=edtEmail.text.toString()
                var password=edtPassword.text.toString()
                loginUserAccount(email,password)
            }
            btnSigIn.setOnClickListener {
                startActivity(Intent(this@LoginScreen,SigninScreen::class.java))
            }
        }
    }
    private fun loginUserAccount(email:String,password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Authentication Successfully.",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }
}