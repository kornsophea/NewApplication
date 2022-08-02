package com.example.newapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.newapplication.databinding.ActivityMainBinding
import com.example.newapplication.fragment.AddFragment
import com.example.newapplication.fragment.HomeFragment
import com.example.newapplication.fragment.SettinngFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding=DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
        //setContentView(R.layout.activity_main)




        binding.apply {

            bottomNavigation.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.home->replaceFragment(HomeFragment())
                    R.id.add->replaceFragment(AddFragment())
                    R.id.setting->replaceFragment(SettinngFragment())
                }
                true
            }
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val transation =supportFragmentManager.beginTransaction()
        transation.replace(R.id.fragment_navigation,fragment)
        transation.commit()
    }
}