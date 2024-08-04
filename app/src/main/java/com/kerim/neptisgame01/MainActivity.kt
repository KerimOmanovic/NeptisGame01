package com.kerim.neptisgame01

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the animation
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        // Find the TextView and start the animation
        val textViewWelcome = findViewById<TextView>(R.id.textViewWelcome)
        textViewWelcome.startAnimation(fadeInAnimation)

        // Navigate to Login Activity after a delay
        val intent = Intent(this, LoginActivity::class.java)
        textViewWelcome.postDelayed({
            startActivity(intent)
            finish()
        }, 3000) // 3000 ms delay to match the animation duration
    }
}
