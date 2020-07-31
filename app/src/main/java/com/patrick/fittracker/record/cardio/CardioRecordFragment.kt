package com.patrick.fittracker.record.cardio

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
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
import com.patrick.fittracker.network.LoadApiStatus
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

    var instantcal: Long = 0
    var recordImage: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CardioRecordFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        initData()

        viewModel.addCardioRecordd.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when(viewModel.cardioItem.value?.cardio_title){
                "羽毛球" -> instantcal = it.duration.times(153).div(30)
                "棒球" -> instantcal = it.duration.times(141).div(30)
                "籃球" -> instantcal = it.duration.times(189).div(30)
                "保齡球" -> instantcal = it.duration.times(108).div(30)
                "攀岩" -> instantcal = it.duration.times(210).div(30)
                "自行車" -> instantcal = it.duration.times(252).div(30)
                "慢跑" -> instantcal = it.duration.times(246).div(30)
                "跳繩" -> instantcal = it.duration.times(252).div(30)
                "戶外體操" -> instantcal = it.duration.times(93).div(30)
                "步行" -> instantcal = it.duration.times(105).div(30)
                "桌球" -> instantcal = it.duration.times(126).div(30)
                "足球" -> instantcal = it.duration.times(231).div(30)
                "衝浪" -> instantcal = it.duration.times(216).div(30)
                "游泳" -> instantcal = it.duration.times(189).div(30)
                "TABATA" -> instantcal = it.duration.times(450).div(30)
                "撞球" -> instantcal = it.duration.times(45).div(30)
                "網球" -> instantcal = it.duration.times(198).div(30)
                "排球" -> instantcal = it.duration.times(108).div(30)
                "健走" -> instantcal = it.duration.times(165).div(30)
                "瑜珈" -> instantcal = it.duration.times(90).div(30)
                "有氧舞蹈" -> instantcal = it.duration.times(204).div(30)
                "飛輪" -> instantcal = it.duration.times(250).div(30)
                "高爾夫" -> instantcal = it.duration.times(150).div(30)
                "划船" -> instantcal = it.duration.times(132).div(30)
                "滑雪" -> instantcal = it.duration.times(216).div(30)
                "拳擊" -> instantcal = it.duration.times(342).div(30)

            }
            viewModel.addCardioRecordd.value?.burnFat = instantcal
        })

        binding.finishButton.setOnClickListener {
            viewModel.showLoadingStatus()

            viewModel.photoUpload.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (viewModel._photoUpload.value == true || viewModel._photoUpload.value == null) {
                viewModel.addCardioRecordd.value?.recordImage = recordImage
                viewModel.addCardioRecordd.value?.name =
                    viewModel.cardioItem.value?.cardio_title.toString()
                viewModel.addCardioRecordd.value?.burnFat = instantcal
                viewModel.addCardioRecordd.value?.let { it1 -> viewModel.uploadCardioRecordData(it1) }
                viewModel.uploadCardioStatusRecord()
                if (viewModel.addCardioRecordd.value != null) {
                    findNavController().navigate(NavigationDirections.actionGlobalCardioFinishFragment())
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong. Please wait!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(),"Loading",Toast.LENGTH_LONG).show()
            }

            })
        }


        binding.cancelButton.setOnClickListener {
            dismiss()
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
            viewModel._photoUpload.value = false
            ref.putFile(it)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        recordImage = it.toString()
                        viewModel._photoUpload.value = recordImage != ""
                    }
                }
        }
    }

}
