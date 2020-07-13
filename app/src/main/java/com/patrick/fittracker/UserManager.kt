package com.patrick.fittracker

import android.content.Context
import android.util.Log
import com.patrick.fittracker.data.User
import com.patrick.fittracker.data.UserProfile


object UserManger {
    val userData = User(userProfile = UserProfile())

//    val pref = MyApplication.appContext.getSharedPreferences("userID", Context.MODE_PRIVATE)
//    var userID: String?
//        get() {
//            //store data in somewhere
//            val userID = pref.getString("user_ID", null)
//            Log.i("get", "get() in login success")
//            return userID
//        }
//        set(a) {
//            //save or change the data
//            val editor = pref.edit()
//            editor.putString("user_ID", a).apply()
//        }
//
//    fun isLogin(): Boolean {
//        return userID != null
//    }



}