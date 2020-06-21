package com.rafiki.chatapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.rafiki.chatapp.R
import com.rafiki.chatapp.goToActivity
import com.rafiki.chatapp.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        if(mAuth.currentUser == null) {
//            toast("No Logado")
//        }else{
//            toast("Logado")
//        mAuth.signOut()
//        }

        buttonLogIn.setOnClickListener {
            val email = editText_email.text.toString()
            val password = editText_password.text.toString()

            if(isValidEmailAndPassword(email,password)){
                logInByEmail(email,password)
            }else{

            }
        }

        textViewForgotPassword.setOnClickListener { goToActivity<ForgotPaswordActivity>() }
        buttonCreateAccount.setOnClickListener { goToActivity<SingUpActivity>() }

    }

    private fun logInByEmail(email:String, password:String){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener (this){ task ->
            if(task.isSuccessful){
                toast("User is now Logged In")
            }else{
                toast("An Unexpected error Ocurred please try Again")
            }
        }
    }

    private fun isValidEmailAndPassword(email:String, password:String): Boolean{
        return !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }
}