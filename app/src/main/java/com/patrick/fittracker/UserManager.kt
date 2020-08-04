package com.patrick.fittracker

import android.content.Context
import android.util.Log
import com.patrick.fittracker.UserManger.userEmail
import com.patrick.fittracker.UserManger.userID
import com.patrick.fittracker.UserManger.userImage
import com.patrick.fittracker.UserManger.userName
import com.patrick.fittracker.data.CurrentLocation
import com.patrick.fittracker.data.User
import com.patrick.fittracker.data.UserProfile

val google_id: String? = userID
val google_name: String? = userName
val google_image: String? = userImage
val google_email: String? = userEmail

object UserManger {
    val userData = User(userProfile = UserProfile())
    val currentLocation = CurrentLocation()

    val pref = FitTrackerApplication.appContext.getSharedPreferences("userID", Context.MODE_PRIVATE)
    var userID: String?
        get() {
            //store data in somewhere
            val userID = pref.getString("user_ID", null)
            Log.i("get", "get() in login success")
            return userID
        }
        set(a) {
            //save or change the data
            val editor = pref.edit()
            editor.putString("user_ID", a).apply()
        }

    val pref_name = FitTrackerApplication.appContext.getSharedPreferences("userName", Context.MODE_PRIVATE)
    var userName: String?
        get() {
            //store data in somewhere
            val userID = pref_name.getString("user_name", null)
            Log.i("get", "get() in login success")
            return userID
        }
        set(a) {
            //save or change the data
            val editor = pref_name.edit()
            editor.putString("user_name", a).apply()
        }

    val pref_image = FitTrackerApplication.appContext.getSharedPreferences("userImage", Context.MODE_PRIVATE)
    var userImage: String?
        get() {
            //store data in somewhere
            val userID = pref_image.getString("user_image", null)
            Log.i("get", "get() in login success")
            return userID
        }
        set(a) {
            //save or change the data
            val editor = pref_image.edit()
            editor.putString("user_image", a).apply()
        }

    val pref_email = FitTrackerApplication.appContext.getSharedPreferences("userEmail", Context.MODE_PRIVATE)
    var userEmail: String?
        get() {
            //store data in somewhere
            val userID = pref_email.getString("user_email", null)
            Log.i("get", "get() in login success")
            return userID
        }
        set(a) {
            //save or change the data
            val editor = pref_email.edit()
            editor.putString("user_email", a).apply()
        }

    fun isLogin(): Boolean {
        return userID != null
    }

}