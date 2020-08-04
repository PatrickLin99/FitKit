package com.patrick.fittracker.calendar.eventcardio

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels

import com.patrick.fittracker.R
import com.patrick.fittracker.TimeUtil
import com.patrick.fittracker.calendar.CalendarViewModel
import com.patrick.fittracker.data.CardioRecord
import com.patrick.fittracker.databinding.CalendarFragmentBinding
import com.patrick.fittracker.databinding.EventDetailCardioFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import java.util.*

class EventDetailCardioFragment : DialogFragment() {

    private val viewModel by viewModels<EventDetailCardioViewModel> { getVmFactory( )}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = EventDetailCardioFragmentBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val recordKey = EventDetailCardioFragmentArgs.fromBundle(requireArguments()).recordKey

        binding.calendarEventDetailCardioTitle.text = recordKey.name
        binding.calendarEventDetailCardioDate.text = TimeUtil.CalendarStampToDate(recordKey.createdTime, Locale.TAIWAN)
        binding.calendarEventDetailCardioDuration.text = "運動時間:  ${recordKey.duration.toString()}"
        binding.calendarEventDetailCardioCalories.text = "消耗熱量:  ${recordKey.burnFat.toString()}"


        return binding.root
    }

}
