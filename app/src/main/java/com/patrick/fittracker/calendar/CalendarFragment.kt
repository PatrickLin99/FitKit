package com.patrick.fittracker.calendar

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialCalendar
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.R
import com.patrick.fittracker.TimeUtil
import com.patrick.fittracker.calendar.events.CalendarEventAdapter
import com.patrick.fittracker.calendar.events.CalendarEventCardioAdapter
import com.patrick.fittracker.databinding.CalendarFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import java.security.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.milliseconds


class CalendarFragment : Fragment() {

    private val viewModel by viewModels<CalendarViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CalendarFragmentBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val dayBegin = "$year-${month.plus(1)}-$dayOfMonth 00:01:00"
            val dayEnd = "$year-${month.plus(1)}-$dayOfMonth 23:59:00"

            val dateBegin = TimeUtil.DateToStamp(dayBegin, Locale.TAIWAN)
            val dateEnd = TimeUtil.DateToStamp(dayEnd, Locale.TAIWAN)

            viewModel.getCalendarTrainingRecordResult(dateBegin, dateEnd)
            viewModel.getCalendarTrainingCardioRecordResult(dateBegin, dateEnd)
        }

        val adapter = CalendarEventAdapter(CalendarEventAdapter.OnClickListener{
            it.let {
                findNavController().navigate(NavigationDirections.actionGlobalEventDetailFragment(it))
            }
        })

        binding.recyclerViewCalendarEvent.adapter = adapter
        viewModel.record.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                adapter.submitList(it.distinctBy { it.name })
            }
        })

        val adapterCardio = CalendarEventCardioAdapter(CalendarEventCardioAdapter.OnClickListener{
            it.let {
                findNavController().navigate(NavigationDirections.actionGlobalEventDetailCardioFragment(it))
            }
        })

        binding.recyclerViewCalendarEventCardio.adapter = adapterCardio
        viewModel.recordCardio.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                adapterCardio.submitList(it.distinctBy { it.name })
            }
        })
        adapter.notifyDataSetChanged()

        return binding.root
    }
}
