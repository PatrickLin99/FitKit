package com.patrick.fittracker

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
import com.patrick.fittracker.util.Logger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.launch


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
//        binding.toolbar.setBackgroundColor(applicationContext.getColor(R.color.colorBlack))
//        binding.mainTitleTwo.setTextColor((applicationContext.getColor(R.color.colorWhite)))

//        setupToolbar()

    }


    private var listener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
//
            when (item.itemId) {
                R.id.navigation_home -> {
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalHomeFragment())
//                    binding.toolbar.setBackgroundColor(applicationContext.getColor(R.color.colorWhite))
//                    binding.mainTitleTwo.setTextColor((applicationContext.getColor(R.color.colorLightBlack)))
                    return true
                }
                R.id.navigation_calendar -> {
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalCalendarFragment())
                    return true
                }
                R.id.navigation_profile -> {
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalProfileFragment())
                    return true
                }
                R.id.navigation_analysis -> {
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalLoginFragment())
//                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.actionGlobalAnalysisFragment())
                    return true
                }
            }
            return true
        }

    }

//    private val statusBarHeight: Int
//        get() {
//            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
//            return when {
//                resourceId > 0 -> resources.getDimensionPixelSize(resourceId)
//                else -> 0
//            }
//        }

//    private fun setupToolbar() {
//
//        binding.toolbar.setPadding(0, statusBarHeight, 0, 0)
//
//        launch {
//
//            val dpi = resources.displayMetrics.densityDpi.toFloat()
//            val dpiMultiple = dpi / DisplayMetrics.DENSITY_DEFAULT
//
//            val cutoutHeight = getCutoutHeight()
//
//            Logger.i("====== ${Build.MODEL} ======")
//            Logger.i("$dpi dpi (${dpiMultiple}x)")
//            Logger.i("statusBarHeight: ${statusBarHeight}px/${statusBarHeight / dpiMultiple}dp")
//
//            when {
//                cutoutHeight > 0 -> {
//                    Logger.i("cutoutHeight: ${cutoutHeight}px/${cutoutHeight / dpiMultiple}dp")
//
//                    val oriStatusBarHeight = resources.getDimensionPixelSize(R.dimen.height_status_bar_origin)
//
//                    binding.toolbar.setPadding(0, oriStatusBarHeight, 0, 0)
//                    val layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT)
//                    layoutParams.gravity = Gravity.CENTER
//                    layoutParams.topMargin = statusBarHeight - oriStatusBarHeight
////                    binding.imageToolbarLogo.layoutParams = layoutParams
////                    binding.textToolbarTitle.layoutParams = layoutParams
//                }
//            }
//            Logger.i("====== ${Build.MODEL} ======")
//        }
//    }
}
