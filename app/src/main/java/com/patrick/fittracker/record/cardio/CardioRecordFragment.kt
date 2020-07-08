package com.patrick.fittracker.record.cardio

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.patrick.fittracker.R
import com.patrick.fittracker.data.AddTrainingRecord
import com.patrick.fittracker.databinding.CardioRecordFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.group.GroupViewModel
import kotlinx.android.synthetic.main.cardio_record_fragment.*

class CardioRecordFragment : DialogFragment() {

    private val viewModel by viewModels <CardioRecordViewModel> { getVmFactory(CardioRecordFragmentArgs.fromBundle(requireArguments()).cardioKey) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CardioRecordFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        binding.uploadCardioRecordButton.setOnClickListener {

            viewModel.uploadCardioStatusRecord()
            viewModel.addTrainingRecordd.value?.let { it1 -> viewModel.uploadCardioRecordData(it1) }

        }




        return binding.root
    }

}
