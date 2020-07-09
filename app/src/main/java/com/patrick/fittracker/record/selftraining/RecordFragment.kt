package com.patrick.fittracker.record.selftraining

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.data.AddTrainingRecord
import com.patrick.fittracker.data.User
import com.patrick.fittracker.databinding.RecordFragmentTestBinding
import com.patrick.fittracker.ext.getVmFactory
import java.io.ObjectStreamException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class RecordFragment : Fragment() {

    var group: SetOrderFilter = SetOrderFilter.ORDER_NUM
    private val viewModel by viewModels<RecordViewModel> { getVmFactory( RecordFragmentArgs.fromBundle(requireArguments()).muscleKey ) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = RecordFragmentTestBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = RecordAdapter()
//        binding.recyclerViewAddList.adapter = adapter

//        val adapter = AdapterTwo()
        binding.recyclerViewShowAddList.adapter = adapter

        viewModel.add.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("99999999999999999","$it")
                adapter.submitList(it)
            }
        })



//        viewModel.navigateToPoseSelect.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.submitList(it.orderNum)
//            }
//        })

        binding.view3.visibility = View.INVISIBLE
        binding.view7.visibility = View.INVISIBLE
        binding.view8.visibility = View.INVISIBLE
        binding.view9.visibility = View.INVISIBLE
        binding.recordAnother.visibility = View.INVISIBLE
        binding.finishRecord.visibility = View.INVISIBLE



        val muscleKey = RecordFragmentArgs.fromBundle(requireArguments()).muscleKey
        binding.recordMuscleMainTitle.text = muscleKey
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

            viewModel.getLiveRecordResult(muscleKey)
            adapter.notifyDataSetChanged()

            viewModel.addTrainingRecordd.value?.let { it1 -> viewModel.uploadRecordData(it1) }
            viewModel.addTrainingRecordd.value?.category_title = muscleKey
            order_title += 1
            viewModel.addTrainingRecordd.value?.order_title = order_title

            binding.view3.visibility = View.VISIBLE
            binding.view7.visibility = View.VISIBLE
            binding.view8.visibility = View.VISIBLE
            binding.view9.visibility = View.VISIBLE
            binding.recordAnother.visibility = View.VISIBLE
            binding.finishRecord.visibility = View.VISIBLE

        }

        binding.finishRecord.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalFinishRecordFragment())
        }

        binding.recordAnother.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalGroupFragment())
        }


        return binding.root
    }

}
