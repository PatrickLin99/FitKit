package com.patrick.fittracker.record.classoption

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.ClassOptionRecordFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.record.cardio.CardioRecordFragmentArgs
import com.patrick.fittracker.record.cardio.CardioRecordViewModel

class ClassOptionRecordFragment : Fragment() {

    private val viewModel by viewModels <ClassOptionRecordViewModel> { getVmFactory(ClassOptionRecordFragmentArgs.fromBundle(requireArguments()).classoptionKey) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ClassOptionRecordFragmentBinding.inflate(inflater, container,false)

        val classoption = ClassOptionRecordFragmentArgs.fromBundle(requireArguments()).classoptionKey

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = ClassOptionRecordAdapter(ClassOptionRecordAdapter.OnClickListener{
                viewModel.navigateToRecord(it)
        })
        binding.recyclerViewClassMenu.adapter = adapter

        classoption?.let {
            adapter.submitList(it.class_menu)
        }

        viewModel.navigateToRecord.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalInnerRecordFragment(it))
            }
        })





        return binding.root
    }

}
