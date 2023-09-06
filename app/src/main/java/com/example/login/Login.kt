package com.example.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class Login : AppCompatActivity() {

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var registerNowTextView: TextView

    // Initialize Firebase Authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonLogin = findViewById(R.id.btn_login_button_text)
        progressBar = findViewById(R.id.progressBar)
        registerNowTextView = findViewById(R.id.registerNow)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@Login, "Email or password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                // Show the ProgressBar when authentication starts
                progressBar.visibility = ProgressBar.VISIBLE

                // Perform Firebase Authentication login here
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@Login, OnCompleteListener { task ->
                        progressBar.visibility = ProgressBar.GONE
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            val exception = task.exception
                            if (exception != null) {
                                Log.e("TAG", "signInWithEmailAndPassword failed: ${exception.message}")
                                // Display an error message to the user, e.g., using a Toast.
                                Toast.makeText(this@Login, "Authentication failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                            }
                            updateUI(null)
                        }
                    })
            }
        }

        // Set an OnClickListener for the "Register Now" TextView
        registerNowTextView.setOnClickListener {
            // Navigate to the registration screen or perform any desired action
            navigateToRegisterScreen()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        // Handle UI updates based on user authentication status
        // You can customize this method as per your app's requirements
    }

    private fun navigateToRegisterScreen() {
        // Create an Intent to navigate to the registration activity
        val intent = Intent(this@Login, Register::class.java)
        startActivity(intent)
        // Optionally, you can finish this activity to prevent going back to it
        finish()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is already logged in, you can add your logic here
            // For example, you can navigate to a different activity
            // or update the UI to show the user as logged in.
        }
    }
}
