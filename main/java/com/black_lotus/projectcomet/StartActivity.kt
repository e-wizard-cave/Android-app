package com.black_lotus.projectcomet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class StartActivity : AppCompatActivity() {

    private lateinit var Signup_button : Button
    private lateinit var Login_button: Button
    private var firebaseUser : FirebaseUser?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)

        Signup_button = findViewById(R.id.Signup_button)
        Login_button = findViewById(R.id.Login_button)

        Signup_button.setOnClickListener{
            val intent = Intent(this@StartActivity, SignupActivity::class.java)

            startActivity(intent)
        }
        Login_button.setOnClickListener{
            val intent = Intent(this@StartActivity, LoginActivity::class.java)

            startActivity(intent)
        }
    }
    private fun CheckSession(){
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if(firebaseUser!=null){
            val intent = Intent(this@StartActivity, MainActivity::class.java)
            //Toast.makeText(applicationContext, "Active Session", Toast.LENGTH_LONG).show()
            startActivity(intent)
            finish()
        }
    }

    override fun onStart(){
        CheckSession()
        super.onStart()
    }
}