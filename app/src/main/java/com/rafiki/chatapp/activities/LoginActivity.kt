package com.rafiki.chatapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rafiki.chatapp.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.editText_email
import kotlinx.android.synthetic.main.activity_login.editText_password


class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val mGoogleApiClient: GoogleApiClient by lazy { getGoogleApiClient() }
    private val RC_GOOGLE_SIGN_IN = 101


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

        buttonLoginGoogle.setOnClickListener {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
        }

        editText_email.validate {
            editText_email.error = if (isValidEmail(it)) null else "Email is not valid"
        }

        editText_password.validate {
            editText_password.error = if (isValidPassword(it)) null else "Password should contain 1 number, 1 lowercase, 1 uppercase, 1 special character and 4 characters at least"
        }

    }

    private fun getGoogleApiClient(): GoogleApiClient{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleApiClient.Builder(this)
            .enableAutoManage(this,this)
            .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
            .build()
    }

    private fun logInByGoogleAccountIntoFireBase(googleAccount: GoogleSignInAccount ){
        val credential = GoogleAuthProvider.getCredential(googleAccount.idToken,null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(this){
            toast("Sign In By Google")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_GOOGLE_SIGN_IN){
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess){
                val account = result.signInAccount
                logInByGoogleAccountIntoFireBase(account!!)
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("Connection Failed")
    }

}