package com.patrick.fittracker.analysis

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
import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.AnalysisFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AnalysisFragment : Fragment() {

    private val viewModel by viewModels<AnalysisViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AnalysisFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel




        viewModel.getTrainingRecordResult()


        viewModel.record.observe(viewLifecycleOwner, Observer {
            it?.let {
                for(i in 0..5) {
                    Log.d("record created time9999999",
                        "${viewModel.record.value?.sortedBy { it.createdTime }
                            ?.get(i)?.createdTime?.toFloat()?.div(1000000000.0)}"
                    )
                    Log.d("record created time9999999",
                        "${viewModel.record.value?.sortedBy { it.createdTime }
                            ?.get(i)?.fitDetail?.maxBy { it.weight }?.weight}"
                    )
                }

            }
        })

        val pattern = "yyyyMMdd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date: String = simpleDateFormat.format(Date())


//        Log.d("simpledateformat", simpleDateFormat.format((viewModel.record.value?.createdTime)))

        viewModel.record.observe(viewLifecycleOwner, Observer {
            it?.let {


        fun setData(){
            val entries: MutableList<Entry> = ArrayList()
//
            for (i in 0..5){
                ((viewModel.record.value?.sortedBy { it.createdTime }?.get(i)?.fitDetail?.maxBy { it.weight }?.weight)?.toFloat()!!).let {(i.toFloat())
//                    (viewModel.record.value?.sortedBy { it.createdTime }?.get(i)?.fitDetail?.maxBy { it.weight }?.weight)?.toFloat()
                        ?.let { it1 ->
                            Entry(
                                it1,
                                it
                            )
                        }
                }?.let { entries.add(it )}
                Log.d("loop println","${viewModel.record.value?.sortedBy { it.createdTime }?.get(i)?.fitDetail?.maxBy { it.weight }?.weight}")
            }
//        }


//        serDataTest()



//        fun setData() {
//            val entries: MutableList<Entry> = ArrayList()
//            viewModel.record.value?.fitDetail?.maxBy { it.weight }?.weight?.toFloat()?.let {
//                Entry(
//                    it, simpleDateFormat.format(viewModel.record.value!!.createdTime).toFloat())
//            }?.let { entries.add(it) }
//            entries.add(Entry(1f, 1f))
//            entries.add(Entry(2f, 4f))
//            entries.add(Entry(3f, 9f))
//            entries.add(Entry(4f, 16f))
//            entries.add(Entry(5f, 25f))
//            entries.add(Entry(6f, 36f))
//            entries.add(Entry(7f, 49f))
//            entries.add(Entry(8f, 64f))
//            entries.add(Entry(9f, 81f))
//            entries.add(Entry(10f, 100f))
//            entries.add(Entry(11f, 121f))
//            entries.add(Entry(12f, 144f))
//            entries.add(Entry(13f, 169f))
//            entries.add(Entry(14f, 6f))
//            entries.add(Entry(15f, 8f))
//            entries.add(Entry(16f, 3f))

            val dataSet = LineDataSet(entries, "Customized values")
            dataSet.color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
            dataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.colorLightBlack)

            //****
            // Controlling X axis
            val xAxis = binding.lineChart.xAxis
            // Set the xAxis position to bottom. Default is top
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            //Customizing x axis value
            val months = arrayOf("M", "T", "W", "T", "F", "S", "S","A","A","A")

            val formatter = IAxisValueFormatter { value, axis -> months[value.toInt()] }
            xAxis.granularity = 1f // minimum axis-step (interval) is 1
//            xAxis.valueFormatter = formatter

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
