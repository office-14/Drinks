package com.office14.coffeedose.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import com.coffeedose.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.iid.FirebaseInstanceId
import com.office14.coffeedose.repository.PreferencesRepository
import kotlinx.android.synthetic.main.activity_main.*

class CoffeeDoseActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareSignIn()
        initToolbar()
        checkFcmRegToken()

        val ex = intent.extras
    }

    private var successAuthCallback : () -> Unit = {}



    private fun prepareSignIn()
    {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        //if (currentUser == null)
            //signIn()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                //updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    getUserAndUpdateToken()
                }
            }
    }

    private fun getUserAndUpdateToken(){
        val user = auth.currentUser
        user?.getIdToken(true)?.addOnCompleteListener(this){ tokenIdTask ->
            if (tokenIdTask.isSuccessful){
                if (tokenIdTask.result != null && tokenIdTask.result!!.token != null) {
                    val token = tokenIdTask.result!!.token!!
                    PreferencesRepository.saveIdToken(token)
                    successAuthCallback()
                }
            }
        }
    }

    fun signIn(successCallback:() -> Unit) {
        successAuthCallback = successCallback
        val signInIntent = googleSignInClient.signInIntent
        signInIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun checkFcmRegToken(){
        val fcmRegToken = PreferencesRepository.getFcmRegToken()
        if (fcmRegToken == PreferencesRepository.EMPTY_STRING){
            getAndUpdateActualFcmToken()
        }
    }

    private fun getAndUpdateActualFcmToken(){
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                if (task.result?.token?.isNotEmpty() == true)
                    PreferencesRepository.saveFcmRegToken(task.result!!.token!!)
            })
    }

    companion object {
       private const val RC_SIGN_IN = 9001
       private const val TAG = "CoffeeDoseActivity"
    }
}
