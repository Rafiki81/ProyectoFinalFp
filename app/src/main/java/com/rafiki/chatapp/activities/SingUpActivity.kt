package com.rafiki.chatapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.rafiki.chatapp.R


class SingUpActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        val currentUser = mAuth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "User is NOT logged IN", Toast.LENGTH_LONG).show()
            //createAccount("rperezbeato@gmail.com", "123456")
        } else {

            Toast.makeText(this, "User IS logged IN", Toast.LENGTH_LONG).show()
        }
        //updateUI(currentUser)

    }

    private fun createAccount(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "CreateUserWithEmail:Succes", Toast.LENGTH_LONG).show()
                    val user = mAuth.currentUser
                } else {
                    Toast.makeText(this, "CreateUserWithEmail:Failure", Toast.LENGTH_LONG).show()
                }
            }
    }
}