package com.rafiki.chatapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.rafiki.chatapp.R
import com.rafiki.chatapp.goToActivity
import com.rafiki.chatapp.toast
import kotlinx.android.synthetic.main.activity_sing_up.*


class SingUpActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        buttonGoLogIn.setOnClickListener{
            goToActivity<LoginActivity>{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        buttonSingUp.setOnClickListener {

            val email = editText_email.text.toString()
            val password = editText_password.text.toString()

            if(isValidEmailAndPassword(email,password)){
                singUpByEmail(email,password)
            }else{
                toast("Please Fill all the data and confirm password is correct")
            }

        }


        val currentUser = mAuth.currentUser
        if (currentUser == null) {
            toast("User is NOT logged IN")
            //createAccount("rperezbeato@gmail.com", "123456")
        } else {

            toast( "User IS logged IN")
        }
        //updateUI(currentUser)

    }

    private fun singUpByEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "An Email has been Sent to You, please confirm before Sign In", Toast.LENGTH_LONG).show()
                    val user = mAuth.currentUser
                } else {
                    Toast.makeText(this, "An Unexpected Error Occurred, please Try Again", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun isValidEmailAndPassword(email: String, password: String): Boolean {

        return !email.isNullOrEmpty() && !password.isNullOrEmpty() && password == editText_confirmPassword.text.toString()

    }
}