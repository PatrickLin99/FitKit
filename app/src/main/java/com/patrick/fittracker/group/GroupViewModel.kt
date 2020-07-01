package com.patrick.fittracker.group

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.patrick.fittracker.data.SelectedMuscleGroup


class GroupViewModel : ViewModel() {



   val poseReturn = MutableLiveData<List<Any>>()


    var db = FirebaseFirestore.getInstance()

//    init {
//        readData()
//    }

    fun readData(group: String) {
        db.collection("muscle_group").document(group)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("task result list", "${task.result?.data?.toList()}")
                    Log.d("task result array", "${task.result?.data?.getValue("menu")}")

                    poseReturn.value = task.result?.data?.getValue("menu") as List<Any>?
                    Log.d("test","${poseReturn.value}")



//                    val menuList = task.result?.toObject(SelectedMuscleGroup::class.java)
//                    Log.d("task result menuList","$menuList")

//                    for (document in ) {
//                        Log.d("FragmentActivity", document.id + " => " + document.data)
//
//
//
////                        author.value = document.getString("author.name")
////                        content.value = document.getString("content")
////                        createdTime.value = document.getLong("createdTime")
////                        id.value = document.getString("id")
////                        tag.value = document.getString("tag")
////                        title.value = document.getString("title")
////                        Log.d("test","$${title.value} ${author.value} ${content.value} ${createdTime.value} ${id.value}")
////
////                        val qq = Info("${author.value}","${content.value}","${createdTime.value}","${id.value}","${tag.value}","${title.value}")
////
////                        ii.add(qq)
////                        mutableLivedata.value = ii
////
//
//                    }
                } else {
                    Log.w("FragmentActivity", "Error getting documents.", task.exception)
                }
            }
    }




}






