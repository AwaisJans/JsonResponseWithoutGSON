package com.json.response.without.gson.app.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.json.response.without.gson.app.R


class LogoScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setBackgroundResource(R.drawable.img)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, FirstScreen::class.java))
            finish()
        },2000)

        val policy = ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)

    }



}