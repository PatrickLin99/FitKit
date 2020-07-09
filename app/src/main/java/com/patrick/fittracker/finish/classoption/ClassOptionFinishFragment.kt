package com.patrick.fittracker.finish.classoption

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.ClassOptionFinishFragmentBinding
import com.patrick.fittracker.databinding.ClassOptionFragmentBinding
import com.patrick.fittracker.databinding.InnerRecordFragmentBinding
import com.patrick.fittracker.ext.getVmFactory

class ClassOptionFinishFragment : Fragment() {


    private val viewModel by viewModels <ClassOptionFinishViewModel> {getVmFactory()}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ClassOptionFinishFragmentBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        return binding.root
    }


}
