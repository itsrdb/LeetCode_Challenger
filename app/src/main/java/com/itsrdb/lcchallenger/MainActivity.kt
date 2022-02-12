package com.itsrdb.lcchallenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.itsrdb.lcchallenger.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav = binding.bottomNav

        val firstFrag = FirstFragment()
        val secondFrag = SecondFragment()
        val thirdFrag = ThirdFragment()

        setCurrentFragment(secondFrag)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.firstFragment->setCurrentFragment(firstFrag)
                R.id.secondFragment->setCurrentFragment(secondFrag)
                R.id.thirdFragment->setCurrentFragment(thirdFrag)
            }
            true
        }

        val dbHelper = SqliteOpenHelper(this, null)
        if(!dbHelper.checkDb()){
            val loginIntent = Intent(this@MainActivity, UserLogin::class.java)
            startActivity(loginIntent)
        }else{
            val user : String = dbHelper.getUser()
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    //for future references
    private fun getSubmissions() {
        val subs = APIService.apiInstance.getSubmissions("itsrdb")
        subs.enqueue(object : Callback<OuterSubmissions>{
            override fun onFailure(call: Call<OuterSubmissions>, t: Throwable) {
                Log.d("oopsie", "Error in Fetching data")
            }

            override fun onResponse(
                call: Call<OuterSubmissions>,
                response: Response<OuterSubmissions>)
            {
                val subs = response.body()
                if(subs!=null){
                    Log.d("Success", subs.toString())
                }
            }
        })
    }

    //for future references
    private fun getRecentSubmissions() {
        val subs = APIService.apiInstance.getRecentSubmissions("itsrdb")
        subs.enqueue(object : Callback<OuterRecentSubmissions>{
            override fun onFailure(call: Call<OuterRecentSubmissions>, t: Throwable) {
                Log.d("oopsie", "Error in Fetching data")
            }

            override fun onResponse(
                call: Call<OuterRecentSubmissions>,
                response: Response<OuterRecentSubmissions>)
            {
                val subs = response.body()
                if(subs!=null){
                    Log.d("Success", subs.toString())
                }
            }
        })
    }
}