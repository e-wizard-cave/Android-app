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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    private lateinit var s_text_username: EditText
    private lateinit var s_text_email: EditText
    private lateinit var s_text_password: EditText
    private lateinit var s_text_M_password: EditText
    private lateinit var f_signup_button: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        //supportActionBar!!.title = "Sign up"
        initiateVariables()
        f_signup_button.setOnClickListener {
            verifyData()
        }
    }

    private fun initiateVariables(){
        s_text_username = findViewById(R.id.S_Text_username)
        s_text_email = findViewById(R.id.S_Text_email)
        s_text_password = findViewById(R.id.S_Text_password)
        s_text_M_password = findViewById(R.id.S_Text_M_password)
        f_signup_button = findViewById(R.id.F_Signup_button)

        auth = FirebaseAuth.getInstance()
    }

    private fun verifyData(){
        val username : String = s_text_username.text.toString()
        val email : String = s_text_email.text.toString()
        val password1 : String = s_text_password.text.toString()
        val password2 : String = s_text_M_password.text.toString()

        if (username.isEmpty()){
            Toast.makeText(applicationContext, "Please enter a username", Toast.LENGTH_SHORT).show()
        }
        else if (email.isEmpty()){
            Toast.makeText(applicationContext, "Please enter an email", Toast.LENGTH_SHORT).show()
        }
        else if (password1.isEmpty()){
            Toast.makeText(applicationContext, "Please enter a password", Toast.LENGTH_SHORT).show()
        }
        else if (password2.isEmpty()){
            Toast.makeText(applicationContext, "Please re-enter your password", Toast.LENGTH_SHORT).show()
        }
        else if (password1 != password2){
            Toast.makeText(applicationContext, "Passwords do not match", Toast.LENGTH_SHORT).show()
        }
        else {
            registerUser(email, password1)
        }
    }

    private fun registerUser(email: String, password1: String){
        auth.createUserWithEmailAndPassword(email, password1)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    var uid : String = ""
                    uid = auth.currentUser!!.uid
                    reference = FirebaseDatabase.getInstance().reference.child("Usernames").child(uid)

                    val hashMap = HashMap<String, Any>()
                    val h_username : String = s_text_username.text.toString()
                    val h_email : String = s_text_email.text.toString()


                    hashMap["uid"] = uid
                    hashMap["n_username"] = h_username
                    hashMap["email"] = h_email
                    hashMap["image"] = ""
                    hashMap["search"] = h_username.lowercase()
                    hashMap["fname"] = ""
                    hashMap["surname"] = ""
                    hashMap["age"] = ""
                    hashMap["Country"] = ""
                    hashMap["State"] = ""
                    hashMap["City"] = ""


                    reference.updateChildren(hashMap).addOnCompleteListener{task2->
                        if(task2.isSuccessful){
                            val intent = Intent(this@SignupActivity, StartActivity::class.java)
                            Toast.makeText(applicationContext, "Sign up successful!", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }
                    }.addOnFailureListener{e->
                        Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(applicationContext, "An error has occurred.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{e->
                Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
            }

    }
}