package com.zvarych.simplechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var ed_email: EditText
    private lateinit var ed_password: EditText
    private lateinit var btn_log_in: Button
    private lateinit var btn_sign_up: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        auth = Firebase.auth
        ed_email = findViewById(R.id.email)
        ed_password = findViewById(R.id.password)
        btn_log_in = findViewById(R.id.btn_log_in)
        btn_sign_up = findViewById(R.id.btn_main_sign_up)

        btn_sign_up.setOnClickListener {
            val intent = Intent(this@LogInActivity, SignUpActivity::class.java)
            finish()
            startActivity(intent)
        }

        btn_log_in.setOnClickListener {
            val email = ed_email.text.toString()
            val password = ed_password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            }
            else {
                Toast.makeText(this@LogInActivity, "Empty data", Toast.LENGTH_SHORT).show()
            }

        }

    }
    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                task ->
            if(task.isSuccessful) {
                val intent = Intent(this, MainActivity::class.java)
                finish()
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}