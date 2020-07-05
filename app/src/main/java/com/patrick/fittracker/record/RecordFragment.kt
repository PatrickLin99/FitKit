package com.patrick.fittracker.record

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.databinding.RecordFragmentBinding
import com.patrick.fittracker.databinding.RecordFragmentTestBinding
import com.patrick.fittracker.ext.getVmFactory


class RecordFragment : Fragment() {

//    companion object {
//        fun newInstance() = RecordFragment()
//    }

    var group: SetOrderFilter = SetOrderFilter.ORDER_NUM

//    private lateinit var viewModel: RecordViewModel
    private val viewModel by viewModels<RecordViewModel> {getVmFactory(group)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.record_fragment, container, false)
        val binding = RecordFragmentTestBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = RecordAdapter()
        binding.recyclerViewAddList.adapter = adapter

        viewModel.getMuscleGroupResult(group)

        viewModel.navigateToPoseSelect.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("test 666666","$it   ${it.orderNum}  ${it.unknown}")
                adapter.submitList(it.orderNum)
            }
        })



        val movement = RecordFragmentArgs.fromBundle(requireArguments()).muscleKey

        binding.recordMuscleMainTitle.text = "${movement}"

        binding.button2.setOnClickListener{
            findNavController().navigate(NavigationDirections.actionGlobalGroupFragment())
        }
        binding.button.setOnClickListener {
//            findNavController().navigate(NavigationDirections.actionGlobalAddSuccessFragment())
        }

//        var setNum : Int = 1
//        binding.setNumber.text = "第 $setNum 組"



        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(RecordViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
