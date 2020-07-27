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
import com.patrick.fittracker.databinding.RecordFragmentTestBinding
import com.patrick.fittracker.databinding.TestLayoutBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.record.cardio.CardioRecordFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cardio_record_fragment.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.record_fragment_test.*
import java.util.*


class RecordFragment : Fragment() {

    var group: SetOrderFilter = SetOrderFilter.ORDER_NUM
    private val viewModel by viewModels<RecordViewModel> { getVmFactory( RecordFragmentArgs.fromBundle(requireArguments()).muscleKey ) }

    private var mStorageRef: StorageReference? = null

    var saveUri: Uri? = null
    var imageUri: String = ""

    private companion object {
        val PHOTO_FROM_GALLERY = 0
        val PHOTO_FROM_CAMERA = 1

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = RecordFragmentTestBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = RecordAdapter()
//        binding.recyclerViewAddList.adapter = adapter

//        val adapter = AdapterTwo()
        binding.recyclerViewShowAddList.adapter = adapter

        viewModel.addInsert.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


//        binding.view3.visibility = View.INVISIBLE
//        binding.view7.visibility = View.INVISIBLE
//        binding.view8.visibility = View.INVISIBLE
//        binding.view9.visibility = View.INVISIBLE
//        binding.view14.visibility = View.INVISIBLE
//        binding.recordAnother.visibility = View.INVISIBLE
//        binding.finishRecord.visibility = View.INVISIBLE
//        binding.addPhoto.visibility = View.INVISIBLE
//        binding.uploadImagePlaceholderWeight.visibility = View.INVISIBLE



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

        var orderNum: Long = 0
        binding.addRecord.setOnClickListener {

            orderNum += 1
            val newRecord = FitDetail()
            newRecord?.count = orderNum
            newRecord?.weight = viewModel.addOne.value?.weight ?: 0
            newRecord?.orderSet = viewModel.addOne.value?.orderSet ?: 0

//            Log.d("test newRecord.fitDetail?.weight","${newRecord?.weight}")

            viewModel.recyclverSho(newRecord)
            adapter.notifyDataSetChanged()

        }

        binding.finishRecord.setOnClickListener {
            viewModel.showLoadingStatus()
//            viewModel.addInsert.value?.let { it1 -> InsertRecord(muscleKey, it1) }?.let { it2 -> viewModel.uploadRecord(insertRecord = it2) }
            Handler().postDelayed({

                viewModel.addInsert.value?.let { it1 -> InsertRecord(muscleKey, it1, 0, imageUri) }
                    ?.let { it2 -> viewModel.uploadRecord(insertRecord = it2) }

                if (viewModel.addInsert.value != null) {
                    findNavController().navigate(NavigationDirections.actionGlobalFinishRecordFragment())
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Some Thing When Wrong, Please Wait!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            },3000)

        }

        binding.recordAnother.setOnClickListener {
            viewModel.showLoadingStatus()
            Handler().postDelayed({
                viewModel.addInsert.value?.let { it1 -> InsertRecord(muscleKey, it1, 0, imageUri) }
                    ?.let { it2 -> viewModel.uploadRecord(insertRecord = it2) }
                if (viewModel.addInsert.value != null) {
                    findNavController().navigate(NavigationDirections.actionGlobalGroupFragment())
                } else{
                    Toast.makeText(requireContext(),"Some Thing When Wrong, Please Wait!",Toast.LENGTH_SHORT).show()
                }
            },3000)

        }

        if (savedInstanceState != null) {
            saveUri = Uri.parse(savedInstanceState.getString("saveUri"))
        }
        permission()

        binding.addPhoto.setOnClickListener {
            toAlbum()
        }

        binding.countDownTimer.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalCountDownTimerFragment())
        }

        return binding.root
    }


    private fun initData() {
        mStorageRef = FirebaseStorage.getInstance().reference
    }

    fun permission() {
        val permissionList = arrayListOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        var size = permissionList.size
        var i = 0
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

    fun toAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
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
//                        upload_image_placeholder_weight.setImageURI(uri)
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
//                        newRecord.recordImage = it.toString()
                        imageUri = it.toString()

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
