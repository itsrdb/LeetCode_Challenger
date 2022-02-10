package com.itsrdb.lcchallenger

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity


class UserLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        //change title bar color kotlin
//        val aBar: ActionBar? = supportActionBar
//        val cd = ColorDrawable(Color.parseColor("#ffffff"))
//        aBar!!.setBackgroundDrawable(cd)

//        val window: Window = this.window
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }
}