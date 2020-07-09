package com.patrick.fittracker.record.classoption.inner

import android.icu.util.Calendar
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.R
import com.patrick.fittracker.cardio.selection.CardioSelectionViewModel
import com.patrick.fittracker.data.AddTrainingRecord
import com.patrick.fittracker.databinding.InnerRecordFragmentBinding
import com.patrick.fittracker.databinding.PoseSelectFragmentBinding
import com.patrick.fittracker.databinding.RecordFragmentTestBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.record.selftraining.RecordAdapter
import com.patrick.fittracker.record.selftraining.RecordFragmentArgs

class InnerRecordFragment : Fragment() {

    private val viewModel by viewModels <InnerRecordViewModel> {getVmFactory()}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = InnerRecordFragmentBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        val adapter = InnerRecordAdapter()
        binding.classOptionRecyclerViewShowAddList.adapter = adapter

        viewModel.add.observe(viewLifecycleOwner, Observer {
            it?.let {
                    adapter.submitList(it)
            }
        })

        binding.view3.visibility = View.INVISIBLE
        binding.view7.visibility = View.INVISIBLE
        binding.view8.visibility = View.INVISIBLE
        binding.view9.visibility = View.INVISIBLE
        binding.recordAnother.visibility = View.INVISIBLE
        binding.finishRecord.visibility = View.INVISIBLE


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


        var order_title : Long = 0
        binding.uploadRecordButton.setOnClickListener {

            viewModel.getClassInnerRecordResult(InnerRecordFragmentArgs.fromBundle(requireArguments()).classKey)
            adapter.notifyDataSetChanged()

            viewModel.addTrainingRecordd.value?.category_title = InnerRecordFragmentArgs.fromBundle(requireArguments()).classKey
            viewModel.addTrainingRecordd.value?.let { it1 -> viewModel.uploadRecordData(it1) }

            order_title += 1
            viewModel.addTrainingRecordd.value?.order_title = order_title

            binding.view3.visibility = View.VISIBLE
            binding.view7.visibility = View.VISIBLE
            binding.view8.visibility = View.VISIBLE
            binding.view9.visibility = View.VISIBLE
            binding.recordAnother.visibility = View.VISIBLE
            binding.finishRecord.visibility = View.VISIBLE
        }

        binding.recordAnother.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalClassOptionFragment())
        }

        binding.finishRecord.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalClassOptionFinishFragment())
        }


        return binding.root
    }

}
