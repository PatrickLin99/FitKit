package com.patrick.fittracker.linechart.cardiochart

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

import com.patrick.fittracker.R
import com.patrick.fittracker.analysis.cardioanalysis.AnalysisCardioViewModel
import com.patrick.fittracker.databinding.CardioChartFragmentBinding
import com.patrick.fittracker.ext.getVmFactory

class CardioChartFragment : Fragment() {

    private val viewModel by viewModels<CardioChartViewModel> { getVmFactory( CardioChartFragmentArgs.fromBundle(requireArguments()).recordKey ) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CardioChartFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val recordKey = CardioChartFragmentArgs.fromBundle(requireArguments()).recordKey

        viewModel.getCardioRecordRecordResult(recordKey)

        viewModel.record.observe(viewLifecycleOwner, Observer {
            it?.let {

                Log.d("viewModel size", "${viewModel.record.value?.size}")

                val cardio_list_size: Int? = viewModel.record.value?.size?.minus(1)

                fun setData() {
                    val entries: MutableList<Entry> = ArrayList()
                    for (i in 0..cardio_list_size!!) {

                        viewModel.record.value?.get(i)?.duration?.toFloat()?.let { it1 ->
                            Entry(i.toFloat(),
                                it1
                            )
                        }?.let { it2 -> entries.add(it2) }

                    }


                    val dataSet = LineDataSet(entries, "Duration (minutes)")
                    dataSet.color =
                        ContextCompat.getColor(requireContext(), R.color.colorAccent)
                    dataSet.valueTextColor =
                        ContextCompat.getColor(requireContext(), R.color.colorLightBlack)
                    dataSet.valueTextSize = 12f
                    dataSet.lineWidth = 2f
                    dataSet


                    //****
                    // Controlling X axis
                    val xAxis = binding.lineChart.xAxis
                    // Set the xAxis position to bottom. Default is top
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    //Customizing x axis value
//                    val months = arrayOf("M", "T", "W", "T", "F", "S", "S", "A", "A", "A")
                    //Set grid color
                    xAxis.gridColor = ContextCompat.getColor(requireContext(), R.color.colorLightGray)


                    //off the xAxis line
                    binding.lineChart.xAxis.isEnabled = false

//                    val formatter = IAxisValueFormatter { value, axis -> months[value.toInt()] }
                    xAxis.granularity = 1f // minimum axis-step (interval) is 1
//                    xAxis.valueFormatter = formatter

                    //***
                    // Controlling right side of y axis
                    val yAxisRight = binding.lineChart.axisRight
                    yAxisRight.isEnabled = false

                    //***
                    // Controlling left side of y axis
                    val yAxisLeft = binding.lineChart.axisLeft
                    yAxisLeft.granularity = 1f

                    // Setting Data
                    val data = LineData(dataSet)
                    binding.lineChart.data = data
                    binding.lineChart.invalidate()

                }
                setData()

            }
        })














        return binding.root
    }

}
