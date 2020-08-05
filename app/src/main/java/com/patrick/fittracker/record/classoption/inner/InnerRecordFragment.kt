package com.patrick.fittracker.record.classoption.inner

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.data.InsertRecord
import com.patrick.fittracker.databinding.InnerRecordFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class InnerRecordFragment : Fragment() {

    private val viewModel by viewModels <InnerRecordViewModel> {getVmFactory()}

    var saveUri: Uri? = null
    var imageUri: String = ""

    private companion object {
        const val PHOTO_FROM_GALLERY = 0
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
        binding.addRecord.setOnClickListener {
            viewModel.showRecordList()
            adapter.notifyDataSetChanged()
        }

        binding.recordAnother.setOnClickListener {
            viewModel._photoUpload.observe(viewLifecycleOwner, Observer {
                if (it == true || it == null) {
                    viewModel.valueInsert(
                        InnerRecordFragmentArgs.fromBundle(
                            requireArguments()
                        ).classKey, imageUri
                    )
                    findNavController().navigate(NavigationDirections.actionGlobalClassOptionFragment())
                } else {
                    viewModel.showLoadingStatus()
                }
            })
        }

        binding.finishRecord.setOnClickListener {
            viewModel._photoUpload.observe(viewLifecycleOwner, Observer {
                if (it == true || it == null) {
                    viewModel.valueInsert(
                        InnerRecordFragmentArgs.fromBundle(
                            requireArguments()
                        ).classKey, imageUri
                    )
                    findNavController().navigate(NavigationDirections.actionGlobalClassOptionFinishFragment())
                } else {
                    viewModel.showLoadingStatus()
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

    private fun permission() {
        val permissionList = arrayListOf(
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

    private fun toAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
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
//                        val uri = data!!.data
                        saveUri = data?.data
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
            viewModel._photoUpload.value = false
            ref.putFile(it)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
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
