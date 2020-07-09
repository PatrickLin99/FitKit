package com.patrick.fittracker.finish

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.FinishRecordFragmentBinding
import com.patrick.fittracker.databinding.RecordFragmentTestBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.record.selftraining.RecordFragmentArgs

class FinishRecordFragment : Fragment() {

//    private lateinit var viewModel: FinishRecordViewModel
    private val viewModel by viewModels<FinishRecordViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FinishRecordFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        return binding.root
    }

}
