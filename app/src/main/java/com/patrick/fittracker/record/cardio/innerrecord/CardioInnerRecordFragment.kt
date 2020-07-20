package com.patrick.fittracker.record.cardio.innerrecord

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.CardioInnerRecordFragmentBinding
import com.patrick.fittracker.databinding.CardioRecordFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.record.cardio.CardioRecordFragmentArgs
import com.patrick.fittracker.record.cardio.CardioRecordViewModel
import kotlinx.android.synthetic.main.cardio_inner_record_fragment.*
import java.io.File

class CardioInnerRecordFragment : Fragment() {


    private val viewModel by viewModels <CardioInnerRecordViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CardioInnerRecordFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        return binding.root
    }


}
