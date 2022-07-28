package com.dhruv.adopem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dhruv.adopem.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.gotoSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailInputEditText.text.toString()
            val pass = binding.pwdInputEditText.text.toString()
            val username = binding.nameInputEditText.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && username.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                    if(it.isSuccessful && firebaseAuth.currentUser!!.isEmailVerified){
                        val intent = Intent(this,AdoptHomepageActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this,"Login Successful", Toast.LENGTH_SHORT).show()
                    }else
                    {
                        Toast.makeText(this,"Error:"+it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else {
                Toast.makeText(this,"Please enter all the details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}