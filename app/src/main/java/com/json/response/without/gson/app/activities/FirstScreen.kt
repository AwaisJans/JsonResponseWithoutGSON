package com.json.response.without.gson.app.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.json.response.without.gson.app.R
import com.json.response.without.gson.app.databinding.ActivityFirstScreenBinding

class FirstScreen : AppCompatActivity() {

    private lateinit var b: ActivityFirstScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(b.root)


        b.btnJsonSampleScreen.setOnClickListener {
            startActivity(Intent(this, JSONScreen::class.java))
        }


    }



}