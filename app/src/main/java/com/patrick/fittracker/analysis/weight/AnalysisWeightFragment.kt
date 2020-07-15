package com.patrick.fittracker.analysis.weight

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.R
import com.patrick.fittracker.analysis.AnalysisAdapter
import com.patrick.fittracker.analysis.AnalysisDetailAdapter
import com.patrick.fittracker.analysis.AnalysisViewModel
import com.patrick.fittracker.databinding.AnalysisFragmentBinding
import com.patrick.fittracker.databinding.AnalysisWeightFragmentBinding
import com.patrick.fittracker.ext.getVmFactory

class AnalysisWeightFragment : Fragment() {



    private val viewModel by viewModels<AnalysisWeightViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AnalysisWeightFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.getTrainingRecordResult()

        val adapter = AnalysisAdapter(AnalysisAdapter.OnClickListener{
                findNavController().navigate(NavigationDirections.actionGlobalWeightChartFragment(it))
        })

        binding.recyclerviewWeightRecord.adapter = adapter

            viewModel.record.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("record distinct","it.distinctBy { it.name }=${it.distinctBy { it.name }}")

                adapter.submitList(it.distinctBy {
                    it.name
                })

            }
        })


        return binding.root
    }
}
