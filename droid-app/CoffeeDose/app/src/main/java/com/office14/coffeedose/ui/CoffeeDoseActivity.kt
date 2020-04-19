package com.office14.coffeedose.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.coffeedose.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.iid.FirebaseInstanceId
import com.office14.coffeedose.di.InjectingSavedStateViewModelFactory
import com.office14.coffeedose.repository.PreferencesRepository
import com.office14.coffeedose.repository.PreferencesRepository.EMPTY_STRING
import com.office14.coffeedose.viewmodels.MenuInfoViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class CoffeeDoseActivity : DaggerAppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    @Inject
    lateinit var message:String

    @Inject
    lateinit var abstractViewModelFactory: InjectingSavedStateViewModelFactory

    private lateinit var menuInfoViewModel : MenuInfoViewModel

    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {

        //(application as CoffeeDoseApplication).appComponent.activityComponent().create().inject(this)

        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        val viewModelFactory = abstractViewModelFactory.create(this)
        menuInfoViewModel = ViewModelProvider(this,viewModelFactory).get(MenuInfoViewModel::class.java)
        setContentView(R.layout.activity_main)

        prepareSignIn()
        initToolbar()
        checkFcmRegToken()
        setUpNavigation()

        handleMenuUpdate()
    }

    private fun setUpNavigation() {
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bttm_nav)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(bottomNavigationView,navHostFragment!!.navController)

        val badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.orderFragment)
        badgeDrawable.number = 2

        val badgeDrawable2 = bottomNavigationView.getOrCreateBadge(R.id.orderAwaitingFragment)
        badgeDrawable2.backgroundColor = resources.getColor(R.color.color_green)
    }

    private fun handleMenuUpdate(){
        menuInfoViewModel.orderDetailsCount.observe(this, Observer {
             if (it == 0)
                 bottomNavigationView.removeBadge(R.id.orderFragment)
            else
                 bottomNavigationView.getOrCreateBadge(R.id.orderFragment).number = it
        })


        menuInfoViewModel.currentOrderBadgeColor.observe(this, Observer {
            val orderStatusBadge = bottomNavigationView.getOrCreateBadge(R.id.orderAwaitingFragment)
            orderStatusBadge.backgroundColor = it
        })
    }

    private var successAuthCallback : () -> Unit = {}

    private fun handleNavigationOnNotification(){
        //Toast.makeText(this,"Intent ex = ${intent?.extras ?: "empty"}", Toast.LENGTH_LONG)
        val ex = intent.extras
        ex?.let {
            if (it.containsKey(FromNotificationKey) && it.getBoolean(FromNotificationKey)){
                val navigateId = it.getInt(DestinationFragmentIDKey)
                if (navigateId == OrderAwatingFragmentID){
                    PreferencesRepository.saveNavigateToOrderAwaitFrag(true)
                }
            }
        }
    }

    private fun prepareSignIn()
    {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNavigationOnNotification()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Toast.makeText(this,"Intent ex = ${intent?.extras ?: "empty"}", Toast.LENGTH_LONG)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                //updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) : Boolean {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        var result = false
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                result = task.isSuccessful
                if (task.isSuccessful) {
                    getUserAndUpdateToken(account)
                }
            }

        return result
    }

    private fun getUserAndUpdateToken(account: GoogleSignInAccount){
        val user = auth.currentUser
        user?.getIdToken(true)?.addOnCompleteListener(this){ tokenIdTask ->
            if (tokenIdTask.isSuccessful){
                if (tokenIdTask.result != null && tokenIdTask.result!!.token != null) {
                    val token = tokenIdTask.result!!.token!!
                    PreferencesRepository.saveIdToken(token)
                    PreferencesRepository.saveUserEmail(account.email!!)
                    menuInfoViewModel.refreshOrderDetailsByUser()
                    menuInfoViewModel.user?.let { it.idToken = token }
                    menuInfoViewModel.updateUser()
                    getAndUpdateActualFcmToken()
                    //menuInfoViewModel.refresh()
                    successAuthCallback()
                }
            }
        }
    }

    fun signIn(successCallback:() -> Unit) {

        if (PreferencesRepository.getUserEmail() != EMPTY_STRING)
            logout()

        successAuthCallback = successCallback
        //try sighnIn with current google token
        //if (!firebaseAuthWithGoogle(PreferencesRepository.getGoogleToken())){
            //if not succeeded try regular way
            val signInIntent = googleSignInClient.signInIntent
            signInIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        //}
    }

    fun logout(){

        if (auth.currentUser != null){
            FirebaseAuth.getInstance().signOut()
            auth.signOut()
            googleSignInClient.signOut()
        }


        with(PreferencesRepository){
            saveUserEmail(EMPTY_STRING)
            saveIdToken(EMPTY_STRING)
            saveFcmRegToken(EMPTY_STRING)
        }

        menuInfoViewModel.refreshOrderDetailsByUser()
        menuInfoViewModel.deleteFcmDeviceTokenOnLogOut()
        //menuInfoViewModel.refresh()
        invalidateOptionsMenu()
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

                if (task.result?.token?.isNotEmpty() == true) {
                    PreferencesRepository.saveFcmRegToken(task.result!!.token!!)
                    menuInfoViewModel.updateFcmDeviceToken()
                }
            })
    }

    fun forceLongPolling(){
        menuInfoViewModel.longPollingOrder()
    }

    fun isStarted() : Boolean = lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)

    companion object {
       private const val RC_SIGN_IN = 9001
       private const val TAG = "CoffeeDoseActivity"

        const val FromNotificationKey = "FromNotificationKey"
        const val DestinationFragmentIDKey = "DestinationFragmentIDKey"
        const val OrderAwatingFragmentID = 12

    }
}
