package com.patrick.fittracker.record.selftraining

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.data.FitDetail
import com.patrick.fittracker.data.InsertRecord
import com.patrick.fittracker.databinding.RecordFragmentTestBinding
import com.patrick.fittracker.ext.getVmFactory


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

        viewModel.addInsert.observe(viewLifecycleOwner, Observer {
            it?.let {
//                Log.d("99999999999999999","$it")
                adapter.submitList(it)
            }
        })


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


        var orderNum : Long = 0
        binding.uploadRecordButton.setOnClickListener {

            orderNum += 1

//            val newRecord = AddTrainingRecord()
//            newRecord.order_title = orderNum
//            newRecord.weight = viewModel.addTrainingRecordd.value?.weight ?: 0
//            newRecord.orderSet = viewModel.addTrainingRecordd.value?.orderSet ?: 0
//
//            viewModel.uploadRecordData(newRecord)



//            val newRecord = FitDetail()
//            newRecord.count = orderNum
//            newRecord.weight = viewModel.addOne.value?.weight ?:0
//            newRecord.orderSet = viewModel.addOne.value?.orderSet ?: 0
//
//            Log.d("test newRecord.fitDetail?.weight","${newRecord?.weight}")



            //---------------------------------------------------------------------
//
            val newRecord = InsertRecord(fitDetail = FitDetail())
            newRecord.name = muscleKey
            newRecord.fitDetail?.count = orderNum
            newRecord.fitDetail?.weight = viewModel.addOne.value?.fitDetail?.weight ?: 0
            newRecord.fitDetail?.orderSet = viewModel.addOne.value?.fitDetail?.orderSet ?: 0

            Log.d("test newRecord.fitDetail?.weight","${newRecord.fitDetail?.weight}")

            viewModel.uploadRecord(newRecord)

            adapter.notifyDataSetChanged()
//            viewModel.addTrainingRecordd.value?.category_title = muscleKey
//            order_title += 1
//            viewModel.addTrainingRecordd.value?.order_title = order_title

//            viewModel.uploadRecord(insertRecord = InsertRecord())

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
