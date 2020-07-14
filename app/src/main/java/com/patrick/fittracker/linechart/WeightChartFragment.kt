package com.patrick.fittracker.linechart

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
import com.github.mikephil.charting.formatter.IAxisValueFormatter

import com.patrick.fittracker.R
import com.patrick.fittracker.analysis.weight.AnalysisWeightViewModel
import com.patrick.fittracker.databinding.AnalysisWeightFragmentBinding
import com.patrick.fittracker.databinding.WeightChartFragmentBinding
import com.patrick.fittracker.ext.getVmFactory

class WeightChartFragment : Fragment() {

//    val recordKey= WeightChartFragmentArgs.fromBundle(requireArguments()).recordKey
    private val viewModel by viewModels<WeightChartViewModel> { getVmFactory( WeightChartFragmentArgs.fromBundle(requireArguments()).recordKey )}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = WeightChartFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val recordKey= WeightChartFragmentArgs.fromBundle(requireArguments()).recordKey

        Log.d("recordKey","${recordKey.name}")

        viewModel.getWeightRecordRecordResult(recordKey)

        viewModel.record.observe(viewLifecycleOwner, Observer {
            it?.let {


                fun setData() {
                    val entries: MutableList<Entry> = ArrayList()
                    for (i in 0..5) {
                        Log.d("bbbbbbbbbbbbbbbb",
                            "${viewModel.record.value?.sortedBy { it.createdTime }
                                ?.get(i)?.fitDetail?.maxBy { it.weight }?.weight}"
                        )

                    viewModel.record.value?.sortedBy { it.createdTime }
                        ?.get(i)?.fitDetail?.maxBy { it.weight }?.weight?.toFloat()
                        ?.let { it1 ->
                            Entry(
                                i.toFloat(),
                                it1
                            )
                        }?.let { it2 ->
                            entries.add(
                                it2
                            )
                        }

                }


                    val dataSet = LineDataSet(entries, "Customized values")
                    dataSet.color =
                        ContextCompat.getColor(requireContext(), R.color.colorAccent)
                    dataSet.valueTextColor =
                        ContextCompat.getColor(requireContext(), R.color.colorLightBlack)


                    //****
                    // Controlling X axis
                    val xAxis = binding.lineChart.xAxis
                    // Set the xAxis position to bottom. Default is top
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    //Customizing x axis value
                    val months = arrayOf("M", "T", "W", "T", "F", "S", "S", "A", "A", "A")

                    //隔線關掉
                    binding.lineChart.xAxis.isEnabled = false

                    val formatter = IAxisValueFormatter { value, axis -> months[value.toInt()] }
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
