package com.patrick.fittracker.analysis

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.AnalysisFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import kotlinx.android.synthetic.main.analysis_fragment.*

class AnalysisFragment : Fragment() {

    private val viewModel by viewModels<AnalysisViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AnalysisFragmentBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


//Part1
        val entries = ArrayList<Entry>()

//Part2
        entries.add(Entry(1f, 10f))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 7f))
        entries.add(Entry(4f, 20f))
        entries.add(Entry(5f, 16f))

//Part3
        val vl = LineDataSet(entries, "My Type")

//Part4
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.colorLightGray
        vl.fillAlpha = R.color.colorLightBlack

//Part5
        binding.lineChart.xAxis.labelRotationAngle = 1f

//Part6
        binding.lineChart.data = LineData(vl)

//Part7
        binding.lineChart.axisRight.isEnabled = false
        binding.lineChart.xAxis.axisMaximum = 0.1f

//Part8
        binding.lineChart.setTouchEnabled(true)
        binding.lineChart.setPinchZoom(true)

//Part9
        binding.lineChart.description.text = "Days"
        binding.lineChart.setNoDataText("No forex yet!")

//Part10
        binding.lineChart.animateX(1800, Easing.EaseInExpo)

//Part11
        val markerView = CustomMarker(requireContext(), R.layout.marker_view)
        binding.lineChart.marker = markerView

        return binding.root
    }

}
