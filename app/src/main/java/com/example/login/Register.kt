package com.example.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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

class Register : AppCompatActivity() {

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonRegister: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var loginNowTextView: TextView

    // Initialize Firebase Authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonRegister = findViewById(R.id.btn_register)
        progressBar = findViewById(R.id.progressBar)
        loginNowTextView = findViewById(R.id.loginNow)

        buttonRegister.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@Register, "Email or password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                // Show the ProgressBar when authentication starts
                progressBar.visibility = ProgressBar.VISIBLE

                // Perform Firebase Authentication registration here
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@Register) { task ->
                        // Hide the ProgressBar when authentication completes
                        progressBar.visibility = ProgressBar.GONE

                        // After registration is successful, show the success message
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success")
                            val user = auth.currentUser
                            updateUI(user)

                            // Show the registration success message
                            val registrationSuccessMessage = findViewById<TextView>(R.id.registrationSuccessMessage)
                            registrationSuccessMessage.visibility = View.VISIBLE
                            registrationSuccessMessage.text = getString(R.string.registration_successful)
                        } else {
                            // If sign-in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(null)
                        }
                    }
            }
        }

        // Set an OnClickListener for the "Click to Login" TextView
        loginNowTextView.setOnClickListener {
            // Navigate to the login screen or perform any desired action
            navigateToLoginScreen()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        // Handle UI updates based on user authentication status
        // You can customize this method as per your app's requirements
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(this@Register, Login::class.java)
        startActivity(intent)
        finish()
    }
}
