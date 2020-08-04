package com.patrick.fittracker.record.selftraining

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.L
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.data.FitDetail
import com.patrick.fittracker.data.InsertRecord
import com.patrick.fittracker.databinding.RecordFragmentBinding
import com.patrick.fittracker.databinding.TestLayoutBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.record.cardio.CardioRecordFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cardio_record_fragment.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.record_fragment.*
import java.util.*


class RecordFragment : Fragment() {

    var group: SetOrderFilter = SetOrderFilter.ORDER_NUM
    private val viewModel by viewModels<RecordViewModel> { getVmFactory( RecordFragmentArgs.fromBundle(requireArguments()).muscleKey ) }

    var saveUri: Uri? = null
    var imageUri: String = ""

    private companion object {
        const val PHOTO_FROM_GALLERY = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = RecordFragmentBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = RecordAdapter()
        binding.recyclerViewShowAddList.adapter = adapter
        viewModel.addInsert.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        val muscleKey = RecordFragmentArgs.fromBundle(requireArguments()).muscleKey
        binding.recordMuscleMainTitle.text = muscleKey

        binding.weightAddButton.setOnClickListener {
            viewModel.plusWeight()
        }
        binding.weightMinButton.setOnClickListener {
            viewModel.minusWeight()
        }
        binding.setAddButton.setOnClickListener {
            viewModel.plusOrderSet()
        }
        binding.setMinButton.setOnClickListener {
            viewModel.minusOrderSet()
        }
        binding.addRecord.setOnClickListener {
            viewModel.showRecordList()
            adapter.notifyDataSetChanged()
        }

        binding.finishRecord.setOnClickListener {
            viewModel.photoUpload.observe(viewLifecycleOwner, Observer {
                if (it == true || it == null) {
                    viewModel.valueInsert(imageUri)
                    findNavController().navigate(
                        NavigationDirections.actionGlobalFinishRecordFragment(muscleKey)
                    )
                } else {
                    viewModel.showLoadingStatus()
                }
            })
        }

        binding.recordAnother.setOnClickListener {
            viewModel.photoUpload.observe(viewLifecycleOwner, Observer {
                if (it == true || it == null) {
                    viewModel.valueInsert(imageUri)
                    findNavController().navigate(NavigationDirections.actionGlobalGroupFragment())
                } else {
                    viewModel.showLoadingStatus()
                }
            })
        }

        binding.addPhoto.setOnClickListener {
            toAlbum()
        }

        binding.countDownTimer.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalCountDownTimerFragment())
        }


        if (savedInstanceState != null) {
            saveUri = Uri.parse(savedInstanceState.getString("saveUri"))
        }
        permission()

        return binding.root
    }


    private fun permission() {
        val permissionList = arrayListOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        var size: Int = permissionList.size
        var i : Int = 0
        while (i < size) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    permissionList[i]
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                permissionList.removeAt(i)
                i -= 1
                size -= 1
            }
            i += 1
        }
        val array = arrayOfNulls<String>(permissionList.size)
        if (permissionList.isNotEmpty()) ActivityCompat.requestPermissions(
            requireActivity(),
            permissionList.toArray(array),
            0
        )
    }

    private fun toAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, RecordFragment.PHOTO_FROM_GALLERY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (saveUri != null) {
            val uriString = saveUri.toString()
            outState.putString("saveUri", uriString)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PHOTO_FROM_GALLERY -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val uri = data!!.data
                        saveUri = data.data
                        uploadImage()
                    }
                    Activity.RESULT_CANCELED -> {
                        Log.wtf("getImageResult", resultCode.toString())
                    }
                }
            }
        }
    }

    private fun uploadImage() {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        saveUri?.let {
            viewModel.photoUpload.value = false
            ref.putFile(it)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        imageUri = it.toString()
                        viewModel.photoUpload.value = imageUri != ""
                    }
                }
        }
    }

    override fun onResume() {
        (activity as AppCompatActivity).bottomNavVIew?.visibility = View.GONE
        super.onResume()
    }

    override fun onStop() {
        (activity as AppCompatActivity).bottomNavVIew?.visibility = View.VISIBLE
        super.onStop()
    }

}
