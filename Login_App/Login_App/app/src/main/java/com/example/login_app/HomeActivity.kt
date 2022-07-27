package com.example.login_app

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlin.CharSequence

enum class ProviderType{
    BASIC,
    GOOGLE
}

class HomeActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var map: GoogleMap



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()                    // (Action Bar) Hide Action Bar
        window.statusBarColor = Color.TRANSPARENT   // (Status Bar) Transparent Color

        setContentView(R.layout.activity_home)


        // Analytic Event to Log In Stats
        val analytics = FirebaseAnalytics.getInstance(this)
        val StatsBundle = Bundle()
        StatsBundle.putString("message","Integracion de Firebase Completa")
        analytics.logEvent("InitScreen",StatsBundle)

        // Bundle Data or Package Data of Previous Activity
        val bundle:Bundle? = intent.extras
        val email: String? = bundle?.getString("email")         // Get Email
        val provider: String? = bundle?.getString("provider")   // Get Provider

        // Save Session
        val prefs:SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()

        createFragment()

        setupCustomSpinner()

        // Setup
        setup(email ?: "", provider ?: "")             // If no parameter assign null

    }

    private fun setup(email: String, provider: String) {
        // Widgets Integration
        val settingsButton: Button = findViewById<Button>(R.id.settingsButton)


        //Drawer menu and BottomNavigationView
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_Layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val botNav: BottomNavigationView = findViewById(R.id.bottomMenu)
        val bottomSheetFragment = BottomSheetFragment()

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        botNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_find-> createMarker()
                R.id.ic_data-> Toast.makeText(this,"Ruta",Toast.LENGTH_SHORT).show()
                R.id.ic_path-> bottomSheetFragment.show(supportFragmentManager,"BottomSheetDialog")
            }

            true
        }



        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home-> Toast.makeText(applicationContext,"Clicked", Toast.LENGTH_SHORT).show()
                R.id.nav_Logout-> {
                    val prefs:SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                    prefs.clear()
                    prefs.apply()
                    FirebaseAuth.getInstance().signOut()    // Firebase Log Out
                    onBackPressed()
                }
            }
            true
        }


        // User Settings Button
        settingsButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupCustomSpinner(){
        val spinnerMenu = findViewById<Spinner>(R.id.spinnerMenu)

        val adapter = myArrayAdapter(this,DataSource.list!!)
        spinnerMenu.adapter = adapter

        spinnerMenu.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent!!.getItemAtPosition(position).toString()
                val init: Int = selectedItem.lastIndexOf('=')

                Toast.makeText(this@HomeActivity, selectedItem.subSequence(init+1, selectedItem.length-1), Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.setOnMyLocationButtonClickListener (this)
        map.setOnMyLocationClickListener(this)
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = true
    }

    private fun createMarker() {
        val coordinates = LatLng(1.207649, -77.287243)
        val marker = MarkerOptions().position(coordinates).title("Mi Casa")
        map.addMarker((marker))
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 20f), 3000, null
        )
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        //Evento para hacer click sobre el puntero de ubicacion actual
        Toast.makeText(this, "Estas en ${p0.latitude}, ${p0.longitude}", Toast.LENGTH_LONG).show()
    }
}