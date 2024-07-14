package com.black_lotus.projectcomet

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome_screen)
        WelcomeLoading()

    }


    fun WelcomeLoading(){
        object : CountDownTimer(4000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                /**TODO("Not yet implemented")**/
            }

            override fun onFinish() {
                val intent = Intent(applicationContext, StartActivity::class.java)
                startActivity(intent)
                finish()
            }

        }.start()
    }
}