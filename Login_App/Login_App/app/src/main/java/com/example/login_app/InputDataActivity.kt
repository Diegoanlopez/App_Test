package com.example.login_app

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class InputDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()                    // (Action Bar) Hide Action Bar
        window.statusBarColor = Color.TRANSPARENT   // (Status Bar) Transparent Color

        setContentView(R.layout.activity_input_data)

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

        // Bundle Data or Package Data of Previous Activity
        val bundle:Bundle? = intent.extras
        val identifier: String? = bundle?.getString("identifier")         // Get Email

        // Widgets Integration
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val enterButton = findViewById<Button>(R.id.enterButton)

        // Button Code
        enterButton.setOnClickListener {
            when (identifier) {

                // User Registration
                "SignUp" -> {
                    if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.text.toString(),
                            passwordEditText.text.toString()).addOnCompleteListener {

                            if (it.isSuccessful){
                                show_HomeActivity(it.result?.user?.email ?: "", ProviderType.BASIC)
                            }else{
                                showAlert("Se ha producido un error autenticando al usuario")
                            }
                        }
                    }
                }

                // User Log In
                "LogIn" -> {
                    if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.text.toString(),
                            passwordEditText.text.toString()).addOnCompleteListener {

                            if (it.isSuccessful){
                                show_HomeActivity(it.result?.user?.email ?: "", ProviderType.BASIC)
                            }else{
                                showAlert("Se ha producido un error autenticando al usuario")
                            }
                        }
                    }
                }
            }
        }


    }

    // Open Home Activity
    private fun show_HomeActivity(email: String, provider: ProviderType){
        val homeIntent: Intent = Intent(this,HomeActivity::class.java).apply {
            putExtra("email",email)                 // Save Email Data to Next Activity
            putExtra("provider",provider.name)      // Save provider Data to Next Activity
        }
        startActivity(homeIntent)
    }

    // Display Error Info
    private fun showAlert(msg:String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
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

}