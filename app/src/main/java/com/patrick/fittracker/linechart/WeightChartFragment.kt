package com.patrick.fittracker.linechart

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.patrick.fittracker.R
import com.patrick.fittracker.TimeUtil
import com.patrick.fittracker.databinding.WeightChartFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import java.util.*
import kotlin.collections.ArrayList

class WeightChartFragment : Fragment() {

    private val viewModel by viewModels<WeightChartViewModel> {
        getVmFactory(WeightChartFragmentArgs.fromBundle(requireArguments()).recordKey)
    }
    lateinit var binding: WeightChartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WeightChartFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val recordKey = WeightChartFragmentArgs.fromBundle(requireArguments()).recordKey
        binding.analysisMuscleTitle.text = recordKey.name
        viewModel.getWeightRecordRecordResult(recordKey)

        viewModel.record.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.insertChartValue()
                setData()
            }
        })

        return binding.root
    }

    private fun setData() {
        val dataSet = LineDataSet(viewModel.entries, "Weight (kg)")
        dataSet.let {
            it.color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
            it.valueTextColor = ContextCompat.getColor(requireContext(), R.color.colorLightBlack)
            it.valueTextSize = 12f
            it.lineWidth = 2f
            //Setting Circle property
            it.setCircleColors(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            it.circleRadius = 4f
        }

        //Border
        binding.lineChart.let {
            it.setDrawBorders(true)
            it.setBorderColor(ContextCompat.getColor(requireContext(), R.color.colorDarkGray))
            it.setBorderWidth(0.5f)
        }

        // Controlling X axis
        binding.lineChart.xAxis.let {
            // Set the xAxis position to bottom. Default is top
            it.position = XAxis.XAxisPosition.BOTTOM
            it.textSize = 14f
            it.textColor = R.color.colorLightBlack
            //Grid property
            it.gridColor = ContextCompat.getColor(requireContext(), R.color.colorLightGray)
            it.gridLineWidth = 1f
            it.granularity = 1f // minimum axis-step (interval) is 1
            it.isGranularityEnabled = true
            it.mAxisRange = 1f
        }

        //x Axis Labels
        binding.lineChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(viewModel.labels)
            labelCount = 4
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(true)
            setDrawGridLines(false)
        }
        binding.lineChart.axisLeft.let {
            it.axisLineWidth = 1f
            it.granularity = 20f
            it.setStartAtZero(true)
            it.setStartAtZero(true)
            it.setAxisMaxValue(200f)
            it.setLabelCount(2, false)
            it.textSize = 14f
            it.textColor = R.color.colorLightBlack
        }

        binding.lineChart.let {
            it.description.isEnabled = false
            it.legend.textSize = 18f
            it.legend.textColor = R.color.colorBlack
            it.xAxis.isEnabled = true

            it.axisRight.isEnabled = false
            it.axisLeft.granularity = 1f
        }

        // Setting Data
        val data = LineData(dataSet)
        binding.lineChart.data = data
        binding.lineChart.invalidate()
        //Setting data line property
        data.setValueTextSize(12f)
        data.setValueTextColor(ContextCompat.getColor(requireContext(), R.color.colorDarkGray))

        //Set background gradient
        dataSet.setDrawFilled(true)
        val fillGradient =
            ContextCompat.getDrawable(requireContext(), R.drawable.chart_gradient_fill)
        dataSet.fillDrawable = fillGradient
    }
}
