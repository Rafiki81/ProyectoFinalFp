package com.rafiki.chatapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.rafiki.chatapp.*
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
            val confirmPassword = editText_confirmPassword.text.toString()
            if(isValidEmail(email) && isValidPassword(password) && isValidConfirmPassword(password,confirmPassword)){
                singUpByEmail(email,password)
            }else {
                toast("Please Fill all the data and confirm password is correct")
            }

        }

        editText_email.validate {
            editText_email.error = if (isValidEmail(it)) null else "Email is not valid"
        }

        editText_password.validate {
            editText_password.error = if (isValidPassword(it)) null else "Password should contain 1 number, 1 lowercase, 1 uppercase, 1 special character and 4 characters at least"
        }

        editText_confirmPassword.validate {
            editText_confirmPassword.error = if (isValidConfirmPassword(editText_password.text.toString(),it)) null else "Confirm password does not match Password"
        }


    }

    private fun singUpByEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener(this){
                        toast("An Email has been Sent to You, please confirm before Sign In")
                        goToActivity<LoginActivity> {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                } else {
                    toast("An Unexpected Error Occurred, please Try Again")
                }
            }
    }

}