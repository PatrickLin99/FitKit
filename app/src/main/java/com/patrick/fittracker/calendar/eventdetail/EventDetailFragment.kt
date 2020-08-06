package com.patrick.fittracker.calendar.eventdetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.patrick.fittracker.R
import com.patrick.fittracker.TimeUtil
import com.patrick.fittracker.databinding.EventDetailFragmentBinding
import com.patrick.fittracker.databinding.WeightChartFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.linechart.WeightChartFragmentArgs
import com.patrick.fittracker.linechart.WeightChartViewModel
import java.util.*

class EventDetailFragment : Fragment() {

    private val viewModel by viewModels<EventDetailViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = EventDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val eventDetail = EventDetailFragmentArgs.fromBundle(requireArguments()).recordKey
        binding.calendarEventDetailTitle.text = eventDetail.name
        binding.calendarEventDetailTitleTime.text = TimeUtil.CalendarStampToDate(eventDetail.createdTime, Locale.TAIWAN)

        val adapter = EventDetailAdapter()
        binding.recyclerViewEventDetail.adapter = adapter
        eventDetail.fitDetail.let {
            adapter.submitList(it.sortedByDescending { it.count }.reversed())
        }

        return binding.root
    }
}
