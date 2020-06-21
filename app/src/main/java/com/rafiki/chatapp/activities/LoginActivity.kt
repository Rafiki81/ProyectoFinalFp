package com.rafiki.chatapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.rafiki.chatapp.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.editText_email
import kotlinx.android.synthetic.main.activity_login.editText_password


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

            if(isValidEmail(email) && isValidPassword(password)){
                logInByEmail(email,password)
            }else{
                toast("Please make sure all the data is correct")
            }
        }

        textViewForgotPassword.setOnClickListener { goToActivity<ForgotPaswordActivity>() }
        buttonCreateAccount.setOnClickListener { goToActivity<SingUpActivity>() }

        editText_email.validate {
            editText_email.error = if (isValidEmail(it)) null else "Email is not valid"
        }

        editText_password.validate {
            editText_password.error = if (isValidPassword(it)) null else "Password should contain 1 number, 1 lowercase, 1 uppercase, 1 special character and 4 characters at least"
        }

    }

    private fun logInByEmail(email:String, password:String){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener (this){ task ->
            if(task.isSuccessful){
                if(mAuth.currentUser!!.isEmailVerified){
                    toast("User is now Logged In")
                }else {
                    toast("User must confirm email first")
                }
            }else{
                toast("An Unexpected error Ocurred please try Again")
            }
        }
    }

}