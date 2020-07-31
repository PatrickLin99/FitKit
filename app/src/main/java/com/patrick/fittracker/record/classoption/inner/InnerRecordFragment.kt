package com.patrick.fittracker.record.classoption.inner

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.R
import com.patrick.fittracker.cardio.selection.CardioSelectionViewModel
import com.patrick.fittracker.data.AddTrainingRecord
import com.patrick.fittracker.data.FitDetail
import com.patrick.fittracker.data.InsertRecord
import com.patrick.fittracker.databinding.InnerRecordFragmentBinding
import com.patrick.fittracker.databinding.PoseSelectFragmentBinding
import com.patrick.fittracker.databinding.RecordFragmentTestBinding
import com.patrick.fittracker.databinding.TestLayoutBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.record.selftraining.RecordAdapter
import com.patrick.fittracker.record.selftraining.RecordFragment
import com.patrick.fittracker.record.selftraining.RecordFragmentArgs
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class InnerRecordFragment : Fragment() {

    private val viewModel by viewModels <InnerRecordViewModel> {getVmFactory()}

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
        val binding = InnerRecordFragmentBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        val adapter = InnerRecordAdapter()
        binding.classOptionRecyclerViewShowAddList.adapter = adapter

        viewModel.addInsert.observe(viewLifecycleOwner, Observer {
            it?.let {
                    adapter.submitList(it)
            }
        })

        binding.recordMuscleMainTitle.text = InnerRecordFragmentArgs.fromBundle(requireArguments()).classKey

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


        var orderNum : Long = 0

        binding.addRecord.setOnClickListener {

            orderNum += 1
            val newRecord = FitDetail()
            newRecord?.count = orderNum
            newRecord?.weight = viewModel.addOne.value?.weight ?: 0
            newRecord?.orderSet = viewModel.addOne.value?.orderSet ?: 0

            viewModel.recyclverSho(newRecord)
            adapter.notifyDataSetChanged()

        }

        binding.recordAnother.setOnClickListener {
            viewModel.showLoadingStatus()

                viewModel.photoUpload.observe(viewLifecycleOwner, Observer {
                    if (viewModel.photoUpload.value == true || viewModel.photoUpload.value == null) {
                        viewModel.addInsert.value?.let { it1 ->
                            InsertRecord(
                                InnerRecordFragmentArgs.fromBundle(
                                    requireArguments()
                                ).classKey, it1, 0, imageUri
                            )
                        }?.let { it2 -> viewModel.uploadClassRecord(insertRecord = it2) }

                        if (viewModel.addInsert.value != null) {
                            findNavController().navigate(NavigationDirections.actionGlobalClassOptionFragment())
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Some Thing When Wrong, Please Wait!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })

        }

        binding.finishRecord.setOnClickListener {
            viewModel.showLoadingStatus()
            viewModel.photoUpload.observe(viewLifecycleOwner, Observer {
                if (viewModel.photoUpload.value == true || viewModel.photoUpload.value == null) {

            viewModel.addInsert.value?.let { it1 -> InsertRecord(InnerRecordFragmentArgs.fromBundle(requireArguments()).classKey, it1,0,imageUri) }?.let { it2 -> viewModel.uploadClassRecord(insertRecord = it2) }

                if (viewModel.addInsert.value != null) {
                    findNavController().navigate(NavigationDirections.actionGlobalClassOptionFinishFragment())
                } else {
                    Toast.makeText(requireContext(),"Some Thing When Wrong, Please Wait!",Toast.LENGTH_LONG).show()
                }

                }
            })

        }

        binding.countDownTimer.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalCountDownTimerFragment())
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
        startActivityForResult(intent, PHOTO_FROM_GALLERY)
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
            viewModel._photoUpload.value = false
            ref.putFile(it)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
//                        newRecord.recordImage = it.toString()
                        imageUri = it.toString()
                        viewModel._photoUpload.value = imageUri != ""

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
