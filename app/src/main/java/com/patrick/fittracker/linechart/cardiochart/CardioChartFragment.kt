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

import com.patrick.fittracker.R
import com.patrick.fittracker.analysis.cardioanalysis.AnalysisCardioViewModel
import com.patrick.fittracker.databinding.CardioChartFragmentBinding
import com.patrick.fittracker.ext.getVmFactory

class CardioChartFragment : Fragment() {

    private val viewModel by viewModels<CardioChartViewModel> {
        getVmFactory(
            CardioChartFragmentArgs.fromBundle(
                requireArguments()
            ).recordKey
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CardioChartFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val recordKey = CardioChartFragmentArgs.fromBundle(requireArguments()).recordKey

        binding.cardioRecordTitle.text = recordKey.name

        viewModel.getCardioRecordRecordResult(recordKey)

        viewModel.record.observe(viewLifecycleOwner, Observer {
            it?.let {

                Log.d("viewModel size", "${viewModel.record.value?.size}")

                val cardio_list_size: Int? = viewModel.record.value?.size?.minus(1)

                fun setData() {
                    val entries: MutableList<Entry> = ArrayList()
                    for (i in 0..cardio_list_size!!) {

                        viewModel.record.value?.get(i)?.duration?.toFloat()?.let { it1 ->
                            Entry(
                                i.toFloat(),
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

                    //Border
                    binding.lineChart.setDrawBorders(true)
                    binding.lineChart.setBorderColor(Color.parseColor("#717171"))
                    binding.lineChart.setBorderWidth(0.5f)

                    //****
                    // Controlling X axis
                    val xAxis = binding.lineChart.xAxis
                    // Set the xAxis position to bottom. Default is top
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    //Customizing x axis value
//                    val months = arrayOf("M", "T", "W", "T", "F", "S", "S", "A", "A", "A")
                    //Grid property
                    xAxis.gridColor =
                        ContextCompat.getColor(requireContext(), R.color.colorLightGray)
                    xAxis.gridLineWidth = 1f

                    //Y-axis line width
                    binding.lineChart.axisLeft.axisLineWidth = 1f
                    binding.lineChart.axisLeft.granularity = 20f
                    binding.lineChart.axisLeft.setStartAtZero(true)
                    binding.lineChart.axisLeft.setStartAtZero(true)
                    binding.lineChart.axisLeft.setAxisMaxValue(100f)


                    //xAxis grid lines
                    binding.lineChart.xAxis.isEnabled = true

//                    val formatter = IAxisValueFormatter { value, axis -> months[value.toInt()] }
                    xAxis.granularity = 1f // minimum axis-step (interval) is 1
                    xAxis.isGranularityEnabled = true
                    xAxis.mAxisRange = 1f
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

                    //Setting data line property
                    data.setValueTextSize(12f)
                    data.setValueTextColor(Color.parseColor("#717171"))

                    //Setting Circle property
                    dataSet.setCircleColors(Color.parseColor("#03DAC5"))
//                    dataSet.circleHoleColor = Color.parseColor("#bfc0c0")
                    dataSet.circleRadius = 4f

                    //Set background gridient color
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        dataSet.setDrawFilled(true)
                        val fillGradient = ContextCompat.getDrawable(requireContext(), R.drawable.chart_gradient_fill)
                        dataSet.fillDrawable = fillGradient
                    }

                }
                setData()


                fun setDataCal() {
                    val entries: MutableList<Entry> = ArrayList()
                    for (i in 0..cardio_list_size!!) {

                        viewModel.record.value?.get(i)?.burnFat?.toFloat()?.let { it1 ->
                            Entry(
                                i.toFloat(),
                                it1
                            )
                        }?.let { it2 -> entries.add(it2) }

                    }


                    val dataSet = LineDataSet(entries, "Burning Calories (kcal)")
                    dataSet.color = Color.parseColor("#aa4465")
//                        ContextCompat.getColor(requireContext(), R.color.colorLightPink)
                    dataSet.valueTextColor =
                        ContextCompat.getColor(requireContext(), R.color.colorLightBlack)
                    dataSet.valueTextSize = 12f
                    dataSet.lineWidth = 2f

                    //Border
                    binding.lineChartCal.setDrawBorders(true)
                    binding.lineChartCal.setBorderColor(Color.parseColor("#ffa69e"))
                    binding.lineChartCal.setBorderWidth(0.5f)

                    //****
                    // Controlling X axis
                    val xAxis = binding.lineChartCal.xAxis
                    // Set the xAxis position to bottom. Default is top
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    //Customizing x axis value
//                    val months = arrayOf("M", "T", "W", "T", "F", "S", "S", "A", "A", "A")
                    //Grid property
                    xAxis.gridColor =
                        ContextCompat.getColor(requireContext(), R.color.colorLightGray)
                    xAxis.gridLineWidth = 1f

                    //Y-axis line width
                    binding.lineChartCal.axisLeft.axisLineWidth = 1f
                    binding.lineChartCal.axisLeft.granularity = 200f
                    binding.lineChartCal.axisLeft.setStartAtZero(true)
                    binding.lineChartCal.axisLeft.setStartAtZero(true)
                    binding.lineChartCal.axisLeft.setAxisMaxValue(1200f)


                    //xAxis grid lines
                    binding.lineChartCal.xAxis.isEnabled = true

//                    val formatter = IAxisValueFormatter { value, axis -> months[value.toInt()] }
                    xAxis.granularity = 1f // minimum axis-step (interval) is 1
                    xAxis.isGranularityEnabled = true
                    xAxis.mAxisRange = 1f
//                    xAxis.valueFormatter = formatter

                    //***
                    // Controlling right side of y axis
                    val yAxisRight = binding.lineChartCal.axisRight
                    yAxisRight.isEnabled = false

                    //***
                    // Controlling left side of y axis
                    val yAxisLeft = binding.lineChartCal.axisLeft
                    yAxisLeft.granularity = 1f

                    // Setting Data
                    val data = LineData(dataSet)
                    binding.lineChartCal.data = data
                    binding.lineChartCal.invalidate()

                    //Setting data line property
                    data.setValueTextSize(12f)
                    data.setValueTextColor(Color.parseColor("#aa4465"))

                    //Setting Circle property
                    dataSet.setCircleColors(Color.parseColor("#aa4465"))
//                    dataSet.circleHoleColor = Color.parseColor("#bfc0c0")
                    dataSet.circleRadius = 4f

                    //Set background gridient color
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        dataSet.setDrawFilled(true)
                        val fillGradient = ContextCompat.getDrawable(requireContext(), R.drawable.cardio_cgart_dradient)
                        dataSet.fillDrawable = fillGradient
                    }


                }
                setDataCal()

            }
        })
        return binding.root
    }

}
