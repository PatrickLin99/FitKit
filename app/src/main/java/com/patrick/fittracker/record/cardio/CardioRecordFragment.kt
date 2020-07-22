package com.patrick.fittracker.record.cardio

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.databinding.CardioRecordFragmentBinding
import com.patrick.fittracker.databinding.TestLayoutBinding
import com.patrick.fittracker.ext.getVmFactory
import kotlinx.android.synthetic.main.cardio_record_fragment.*
import kotlinx.android.synthetic.main.home_fragment.*
import java.io.File
import java.util.*

class CardioRecordFragment : DialogFragment() {

    private val viewModel by viewModels <CardioRecordViewModel> { getVmFactory(CardioRecordFragmentArgs.fromBundle(requireArguments()).cardioKey) }

    private var mStorageRef: StorageReference? = null

     var saveUri: Uri? = null

    private companion object {
        val PHOTO_FROM_GALLERY = 0
        val PHOTO_FROM_CAMERA = 1

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CardioRecordFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        initData()

        binding.finishButton.setOnClickListener {

            viewModel.uploadCardioStatusRecord()
            viewModel.addCardioRecordd.value?.name = viewModel.cardioItem.value?.cardio_title.toString()
            viewModel.addCardioRecordd.value?.let { it1 -> viewModel.uploadCardioRecordData(it1) }
            if (viewModel.addCardioRecordd.value != null) {
                findNavController().navigate(NavigationDirections.actionGlobalCardioFinishFragment())
            } else {
                Toast.makeText(requireContext(),"Something went wrong. Please wait!",Toast.LENGTH_LONG).show()
            }

        }

        if (savedInstanceState != null) {
            saveUri = Uri.parse(savedInstanceState.getString("saveUri"))
        }
        permission()

        binding.toAlbum.setOnClickListener {
            toAlbum()
        }
        return binding.root
    }


    private fun initData() {
        mStorageRef = FirebaseStorage.getInstance().reference
    }

    fun permission() {
        val permissionList = arrayListOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        var size = permissionList.size
        var i = 0
        while (i < size) {
            if (ActivityCompat.checkSelfPermission(requireContext(), permissionList[i]) == PackageManager.PERMISSION_GRANTED) {
                permissionList.removeAt(i)
                i -= 1
                size -= 1
            }
            i += 1
        }
        val array = arrayOfNulls<String>(permissionList.size)
        if (permissionList.isNotEmpty()) ActivityCompat.requestPermissions(requireActivity(), permissionList.toArray(array), 0)
    }

    fun toAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, PHOTO_FROM_GALLERY)
    }

//    fun toCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        val tmpFile = File(Environment.getExternalStorageDirectory().toString(), System.currentTimeMillis().toString() + ".jpg")
//        val uriForCamera = FileProvider.getUriForFile(requireContext(), "com.example.aria.day3_image_pickerintentimageview.fileprovider", tmpFile)
//
//        saveUri = uriForCamera
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForCamera)
//        startActivityForResult(intent, PHOTO_FROM_CAMERA)
//    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(saveUri != null){
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
                        upload_image_placeholder.setImageURI(uri)
                        saveUri = data.data
                        saveUri.let {
                            uploadImage()
                        }

                    }
                    Activity.RESULT_CANCELED -> {
                        Log.wtf("getImageResult", resultCode.toString())
                    }
                }
            }

            PHOTO_FROM_CAMERA -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
//                        Glide.with(this).load(saveUri).into(imageView)
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
            ref.putFile(it)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        viewModel.addCardioRecordd.value?.recordImage = it.toString()
                    }
                }
        }
    }

}
