package com.black_lotus.projectcomet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var l_text_email : EditText
    private lateinit var l_text_password : EditText
    private lateinit var f_login_button : Button
    private lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        //supportActionBar!!.title = "Sign up"
        initiateVariables()

        f_login_button.setOnClickListener{
            verifyData()
        }

    }

    private fun initiateVariables(){
        l_text_email = findViewById(R.id.L_Text_email)
        l_text_password = findViewById(R.id.L_Text_password)
        f_login_button = findViewById(R.id.F_Login_button)
        auth = FirebaseAuth.getInstance()
    }

    private fun verifyData(){
        val email : String = l_text_email.text.toString()
        val password : String = l_text_password.text.toString()

        if(email.isEmpty()){
            Toast.makeText(applicationContext, "Please enter your email", Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()){
            Toast.makeText(applicationContext, "Please enter your password", Toast.LENGTH_SHORT).show()
        }
        else{
            loginUserVerified(email, password)
        }


    }

    private fun loginUserVerified(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    Toast.makeText(applicationContext, "Log in successful!", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(applicationContext, "Invalid email or password.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{e->
                Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

}