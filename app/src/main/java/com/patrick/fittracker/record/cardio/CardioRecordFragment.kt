package com.patrick.fittracker.record.cardio

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.patrick.fittracker.NavigationDirections

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


        binding.viewFinish.setOnClickListener {

            viewModel.uploadCardioStatusRecord()
            viewModel.addCardioRecordd.value?.name = viewModel.cardioItem.value?.cardio_title.toString()
            viewModel.addCardioRecordd.value?.let { it1 -> viewModel.uploadCardioRecordData(it1) }
            if (viewModel.addCardioRecordd.value != null) {
                findNavController().navigate(NavigationDirections.actionGlobalCardioFinishFragment())
            } else {
                Toast.makeText(requireContext(),"Something went wrong. Please wait!",Toast.LENGTH_LONG).show()
            }

        }

        return binding.root
    }

}
