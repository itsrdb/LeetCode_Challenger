package com.itsrdb.lcchallenger

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.itsrdb.lcchallenger.databinding.ActivityMainBinding
import com.itsrdb.lcchallenger.databinding.ActivityUserLoginBinding


class UserLogin : AppCompatActivity() {

    private lateinit var binding: ActivityUserLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ContinueBtn = binding.btnContinue
        ContinueBtn.setOnClickListener {
            addUsernameToDB(binding.tvUsername.text.toString())
        }
    }

    private fun addUsernameToDB(username : String) {
        Log.d("username", username)
        val dbHelper = SqliteOpenHelper(this, null)
        dbHelper.addUsername(username)
    }
}