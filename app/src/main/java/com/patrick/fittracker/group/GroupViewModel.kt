package com.patrick.fittracker.group

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.patrick.fittracker.data.SelectedMuscleGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class GroupViewModel : ViewModel() {


    private var _navigateToPoseSelect = MutableLiveData<SelectedMuscleGroup>()

    val navigateToPoseSelect : LiveData<SelectedMuscleGroup>
    get() = _navigateToPoseSelect


    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)



    var db = FirebaseFirestore.getInstance()

    fun readData(group: String) {
        db.collection("muscle_group").document(group)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

//                    Log.d("task result array", "${task.result?.data?.getValue("menu")}")

//                    poseReturn.value = task.result?.data?.getValue("menu") as SelectedMuscleGroup

                    val selectedMuscleGroup = task.result?.toObject(SelectedMuscleGroup::class.java)
//                    Log.d("test selectedMuscleGroup:", "$selectedMuscleGroup")

                    _navigateToPoseSelect.value = selectedMuscleGroup

                }
                    Log.w("FragmentActivity", "Error getting documents.", task.exception)
                }
            }

    fun navigationToSelect(selectedMuscleGroup: SelectedMuscleGroup) {

        coroutineScope.launch {
            _navigateToPoseSelect.value = selectedMuscleGroup
        }
    }
}






