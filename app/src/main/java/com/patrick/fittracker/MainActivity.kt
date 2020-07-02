package com.patrick.fittracker

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.patrick.fittracker.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        val toolbar = supportActionBar
        toolbar?.setDisplayHomeAsUpEnabled(true)

        bottomNavVIew.setOnNavigationItemSelectedListener(listener)

        val db = FirebaseFirestore.getInstance()

        // Create a new user with a first and last name
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Ada"
        user["last"] = "Lovelace"
        user["born"] = 1815

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference -> Log.d("FragmentActivity.TAG", "DocumentSnapshot added with ID: " + documentReference.id) }
            .addOnFailureListener { e -> Log.w("FragmentActivity.TAG", "Error adding document", e) }

    }


    private var listener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
//
            when (item.itemId) {
                R.id.navigation_home -> {
                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_homeFragment)
                    return true
                }
                R.id.navigation_calendar -> {
                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_calendarFragment)
                    return true
                }
                R.id.navigation_profile -> {
                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_profileFragment)
                    return true
                }
                R.id.navigation_analysis -> {
                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_analysisFragment)
                    return true
                }
            }
            return true
        }

    }
}
