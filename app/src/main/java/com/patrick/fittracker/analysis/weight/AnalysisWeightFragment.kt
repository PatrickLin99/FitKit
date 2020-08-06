package com.patrick.fittracker.analysis.weight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections

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

        val adapter = AnalysisAdapter(
            AnalysisAdapter.OnClickListener {
                findNavController().navigate(NavigationDirections.actionGlobalWeightChartFragment(it))
            })

        binding.recyclerviewWeightRecord.adapter = adapter

        viewModel.record.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.distinctBy {
                    it.name
                })
            }
        })

        return binding.root
    }
}
