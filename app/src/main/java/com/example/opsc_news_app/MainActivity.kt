package com.example.opsc_news_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.opsc_news_app.fragments.Weather

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_weather)
        Weather.context = applicationContext
        Weather.activity = this
        Weather.requestPermissions()


    }

}