package com.patrick.fittracker.record.cardio

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.CardioRecordFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.group.GroupViewModel

class CardioRecordFragment : Fragment() {


    private val viewModel by viewModels <CardioRecordViewModel> {getVmFactory()}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.cardio_record_fragment, container, false)
        val binding = CardioRecordFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val cardio = CardioRecordFragmentArgs.fromBundle(requireArguments()).cardioKey
//        Log.d("cardio key safe args","$cardio")








        return binding.root
    }

}
