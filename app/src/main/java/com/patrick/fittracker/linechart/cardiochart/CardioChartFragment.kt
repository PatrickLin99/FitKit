package com.patrick.fittracker.linechart.cardiochart

import android.graphics.Color
import android.os.Build
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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

import com.patrick.fittracker.R
import com.patrick.fittracker.TimeUtil
import com.patrick.fittracker.analysis.cardioanalysis.AnalysisCardioViewModel
import com.patrick.fittracker.databinding.CardioChartFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import java.util.*
import kotlin.collections.ArrayList

class CardioChartFragment : Fragment() {

    private val viewModel by viewModels<CardioChartViewModel> {
        getVmFactory(
            CardioChartFragmentArgs.fromBundle(
                requireArguments()
            ).recordKey
        )
    }

    lateinit var binding: CardioChartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CardioChartFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val recordKey = CardioChartFragmentArgs.fromBundle(requireArguments()).recordKey
        binding.cardioRecordTitle.text = recordKey.name
        viewModel.getCardioRecordRecordResult(recordKey)

        viewModel.record.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.insertChartValue()
                setDataDuration()
                setDataCalories()
            }
        })

        return binding.root
    }

    private fun setDataDuration() {

        val dataSet = LineDataSet(viewModel.entriesDuration, "Duration (minutes)")
        dataSet.let {
            it.color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
            it.valueTextColor = ContextCompat.getColor(requireContext(), R.color.colorLightBlack)
            it.valueTextSize = 12f
            it.lineWidth = 2f
            it.setCircleColors(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            it.circleRadius = 4f
        }

        binding.lineChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(viewModel.labels)
            labelCount = 4
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(true)
            setDrawGridLines(false)
        }

        binding.lineChart.let {
            //Border
            it.setDrawBorders(true)
            it.setBorderColor(R.color.colorDarkGray)
            it.setBorderWidth(0.5f)
        }

        // Controlling X axis
        binding.lineChart.xAxis.let {
            // Set the xAxis position to bottom. Default is top
            it.position = XAxis.XAxisPosition.BOTTOM
            it.textSize = 14f
            it.textColor = R.color.colorLightBlack
            it.granularity = 50f
            //Grid property
            it.gridColor = ContextCompat.getColor(requireContext(), R.color.colorLightGray)
            it.gridLineWidth = 1f
            it.isEnabled = true
        }

        binding.lineChart.axisLeft.let {
            //Y-axis line width
            it.axisLineWidth = 1f
            it.setStartAtZero(true)
            it.setStartAtZero(true)
            it.setAxisMaxValue(200f)
            it.setLabelCount(2, false)
            it.textSize = 14f
            it.textColor = R.color.colorLightBlack
            it.granularity = 1f
            it.isGranularityEnabled = true
            it.mAxisRange = 1f
        }

        binding.lineChart.let {
            it.description.isEnabled = false
            it.legend.textSize = 18f
            it.legend.textColor = R.color.colorBlack
        }

        binding.lineChart.axisRight.isEnabled = false
        binding.lineChart.axisLeft.granularity = 1f

        // Setting Data
        val data = LineData(dataSet)
        binding.lineChart.data = data
        binding.lineChart.invalidate()

        //Setting data line property
        data.setValueTextSize(12f)
        data.setValueTextColor(R.color.colorDarkGray)

        //Set background gridient color
        dataSet.setDrawFilled(true)
        val fillGradient =
            ContextCompat.getDrawable(requireContext(), R.drawable.chart_gradient_fill)
        dataSet.fillDrawable = fillGradient
    }

    private fun setDataCalories() {

        val dataSet = LineDataSet(viewModel.entriesCalories, "BurnFat (Calories)")
        dataSet.let {
            it.color = Color.parseColor("#aa4465")
            it.valueTextColor = ContextCompat.getColor(requireContext(), R.color.colorLightBlack)
            it.valueTextSize = 12f
            it.lineWidth = 2f
            it.setCircleColors(Color.parseColor("#aa4465"))
            it.circleRadius = 4f
        }

        binding.lineChartCal.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(viewModel.labels)
            labelCount = 4
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(true)
            setDrawGridLines(false)
        }

        binding.lineChartCal.let {
            //Border
            it.setDrawBorders(true)
            it.setBorderColor(R.color.colorDarkGray)
            it.setBorderWidth(0.5f)
        }

        // Controlling X axis
        binding.lineChartCal.xAxis.let {
            // Set the xAxis position to bottom. Default is top
            it.position = XAxis.XAxisPosition.BOTTOM
            it.textSize = 14f
            it.textColor = R.color.colorLightBlack
            it.granularity = 50f
            //Grid property
            it.gridColor = ContextCompat.getColor(requireContext(), R.color.colorLightGray)
            it.gridLineWidth = 1f
            it.isEnabled = true
        }

        binding.lineChartCal.axisLeft.let {
            //Y-axis line width
            it.axisLineWidth = 1f
            it.setStartAtZero(true)
            it.setStartAtZero(true)
            it.setAxisMaxValue(1200f)
            it.setLabelCount(3, false)
            it.textSize = 14f
            it.textColor = R.color.colorLightBlack
            it.granularity = 1f
            it.isGranularityEnabled = true
            it.mAxisRange = 1f
        }

        binding.lineChartCal.let {
            it.description.isEnabled = false
            it.legend.textSize = 18f
            it.legend.textColor = R.color.colorBlack
        }

        binding.lineChartCal.axisRight.isEnabled = false
        binding.lineChartCal.axisLeft.granularity = 1f

        // Setting Data
        val data = LineData(dataSet)
        binding.lineChartCal.data = data
        binding.lineChartCal.invalidate()

        //Setting data line property
        data.setValueTextSize(12f)
        data.setValueTextColor(R.color.colorDarkGray)

        //Set background gradient color
        dataSet.setDrawFilled(true)
        val fillGradient =
            ContextCompat.getDrawable(requireContext(), R.drawable.cardio_cgart_dradient)
        dataSet.fillDrawable = fillGradient
    }

}
