package com.dhruv.adopem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dhruv.adopem.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener{
            val email = binding.emailInputEditText.text.toString()
            val pass = binding.pwdInputEditText.text.toString()
            val username = binding.nameInputEditText.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && username.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{ it1 ->
                    if(it1.isSuccessful){
                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this,"SignUp Successful", Toast.LENGTH_SHORT).show()
                        firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener{
                            if (it.isSuccessful){
                                Toast.makeText(this,"Email Verification also sent",Toast.LENGTH_LONG).show()
                            }else {
                                Toast.makeText(this,"couldn't send verification email",Toast.LENGTH_LONG).show()
                            }
                        }
                    }else
                    {
                        Toast.makeText(this,"Error:"+it1.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else {
                Toast.makeText(this,"Please enter all the details", Toast.LENGTH_SHORT).show()
            }
        }

        binding.gotoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}