package com.zvarych.simplechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var ed_username: EditText
    private lateinit var ed_email: EditText
    private lateinit var ed_password: EditText
    private lateinit var btn: Button
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var login: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()


        ed_username = findViewById(R.id.username)
        ed_email = findViewById(R.id.email)
        ed_password = findViewById(R.id.password)
        btn = findViewById(R.id.btn_log_in)
        login = findViewById(R.id.txt_log_in)

        login.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            finish()
            startActivity(intent)
        }



        btn.setOnClickListener {
            val email = ed_email.text.toString()
            val password = ed_password.text.toString()
            val username = ed_username.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
                signup(email, password, username)
            } else {
                Toast.makeText(this@SignUpActivity, "Empty data", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun signup(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                addUserToDatabase(username, email, auth.currentUser?.uid!!)
                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                finish()
                startActivity(intent)
            } else {
                Toast.makeText(this@SignUpActivity, "You enter incorrect data", Toast.LENGTH_SHORT)
                    .show()
                Log.e("AUTH", "Error signing in", task.exception)
            }
        }
    }

    private fun addUserToDatabase(username: String, email: String, uid: String) {
        database = FirebaseDatabase.getInstance().getReference()

        database.child("user").child(uid)
            .setValue(User(username,email,uid))

    }

}