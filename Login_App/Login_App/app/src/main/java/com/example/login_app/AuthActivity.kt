package com.example.login_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_auth.*
import org.w3c.dom.Text

class AuthActivity : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {

        // Visualize Initially App
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()                    // (Action Bar) Hide Action Bar
        window.statusBarColor = Color.TRANSPARENT   // (Status Bar) Transparent Color

        setContentView(R.layout.activity_auth)

        // App Always Time in Vertical Mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Remain in Immersive Mode Previous to When User Make Any Bar Action
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY             // (Immersion Mode) Put in Immersion Mode (Hide All Bar at Any Moment)
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE             // (Immersion Mode) Optimization
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN         // (Fullscreen) Guarantee FullScreen
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION           // (Navigation Bar) Hide Navigation Bar
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION    // (Navigation Bar) Optimization
                or View.STATUS_BAR_HIDDEN                        // (Status Bar) Transition & Hide Notifications (Color change in Status Bar not included)
                )

        // When Previous Session is Open then go to Home Activity
        Check_Session()

        // Button Codes
        Setup()

        val textView:TextView = findViewById(R.id.inicioSesion)
        val text = "Ya tienes una cuenta? Inicia sesion"
        val spannableString = SpannableString(text)
        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                show_InputDataActivity("LogIn")
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color =  Color.BLACK
                ds.isUnderlineText = false
            }
        }

        spannableString.setSpan(clickableSpan1, 22, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.setText(spannableString, TextView.BufferType.SPANNABLE)
        textView.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun Check_Session() {
        val prefs:SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email:String? = prefs.getString("email",null)
        val provider:String? = prefs.getString("provider",null)

        if (email != null && provider != null){
            show_HomeActivity(email,ProviderType.valueOf(provider))
        }
    }

    private fun Setup(){

        // Widgets Integration
        val SignUpButton: Button = findViewById<Button>(R.id.SignUpButton)
        //val LogInButton = findViewById<Button>(R.id.LogInButton)
        val GoogleButton = findViewById<Button>(R.id.GoogleButton)




        // User Registration Button
        SignUpButton.setOnClickListener {
            show_InputDataActivity("SignUp")
        }

        // User Log In Button
        //* LogInButton.setOnClickListener {
         //   show_InputDataActivity("LogIn")
        //}

        // Google Log In Button
        GoogleButton.setOnClickListener {

            // Configuration
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this,googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent,GOOGLE_SIGN_IN)
        }
    }



    // Display Error Info
    private fun showAlert(msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    // Open Home Activity
    private fun show_HomeActivity(email: String, provider: ProviderType){
        val homeIntent: Intent = Intent(this,HomeActivity::class.java).apply {
            putExtra("email",email)                 // Save Email Data to Next Activity
            putExtra("provider",provider.name)      // Save provider Data to Next Activity
        }
        startActivity(homeIntent)
    }

    // Open Input Data Activity
    private fun show_InputDataActivity(identifier: String){
        val homeIntent: Intent = Intent(this,InputDataActivity::class.java).apply {
            putExtra("identifier",identifier)       // Save Identifier Data to Next Activity
        }
        startActivity(homeIntent)
    }

    // Focus Function to Remain in Immersive Mode When User Make Any Bar Action
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus){
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY             // (Immersion Mode) Put in Immersion Mode (Hide All Bar at Any Moment)
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE             // (Immersion Mode) Optimization
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN         // (Fullscreen) Guarantee FullScreen
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION           // (Navigation Bar) Hide Navigation Bar
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION    // (Navigation Bar) Optimization
                    or View.STATUS_BAR_HIDDEN                        // (Status Bar) Transition & Hide Notifications (Color change in Status Bar not included)
                    )
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            show_HomeActivity(account.email ?: "",ProviderType.GOOGLE)
                        }else{
                            showAlert("Se ha producido un error autenticando al usuario")
                        }
                    }
                }
            }catch (e:ApiException){
                showAlert(e.toString())
            }
        }
    }
}