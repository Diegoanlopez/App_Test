package com.example.login_app

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()                    // (Action Bar) Hide Action Bar
        window.statusBarColor = Color.TRANSPARENT   // (Status Bar) Transparent Color

        setContentView(R.layout.activity_splash)

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

        // After Delay Time Open Main Activity
        CoroutineScope(Dispatchers.Main).launch {
            delay(3280L)
            startActivity(Intent(this@SplashActivity,AuthActivity::class.java))
            finish()
        }
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