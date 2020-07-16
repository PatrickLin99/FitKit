package com.patrick.fittracker.calendar

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.patrick.fittracker.databinding.CalendarFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import java.security.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {

    private val viewModel by viewModels<CalendarViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.calendar_fragment, container, false)
        val binding = CalendarFragmentBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel



        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var time : String = "$year$month$dayOfMonth"


            val startDate = "$year-$month-$dayOfMonth 00:01"
            var endDate = "$year-$month-$dayOfMonth 23:59"

            val dateFormat_yyyyMMddHHmm = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.TAIWAN)
            val startdate = dateFormat_yyyyMMddHHmm.parse(startDate)
            val enddate = dateFormat_yyyyMMddHHmm.parse(endDate)
            val calendar = Calendar.getInstance()
            val endcalendar = Calendar.getInstance()
            calendar.time = startdate
            endcalendar.time = enddate
            
            var timestampStart = TimeStamp(startdate)
            Log.d("calendar time","$startdate   ${calendar.timeInMillis}   ${endcalendar.timeInMillis}")

            viewModel.getCalendarTrainingRecordResult(calendar.timeInMillis, endcalendar.timeInMillis)

        }

        viewModel.record.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
//                Log.d("888888888","${viewModel.record.value?.sortedWith(200,300)}")
            }

        })


//        val stringDate="2019-08-07 09:00:00"
//        val dateFormat_yyyyMMddHHmmss = SimpleDateFormat(
//            "yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
//        val date = dateFormat_yyyyMMddHHmmss.parse(stringDate)
//        val calendar = Calendar.getInstance()
//        calendar.setTime(date)


        return binding.root
    }

}
