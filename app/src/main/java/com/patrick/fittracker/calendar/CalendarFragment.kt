package com.patrick.fittracker.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.CalendarFragmentBinding
import java.util.*


class CalendarFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarFragment()
    }

    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.calendar_fragment, container, false)
        val binding = CalendarFragmentBinding.inflate(inflater, container,false)


        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var time : String = "$year + $month + $dayOfMonth"
            Toast.makeText(requireContext(),"show time: $time",Toast.LENGTH_SHORT).show()
        }



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
