package com.patrick.fittracker.record.cardio.innerrecord

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.patrick.fittracker.R

class CardioInnerRecordFragment : Fragment() {

    companion object {
        fun newInstance() = CardioInnerRecordFragment()
    }

    private lateinit var viewModel: CardioInnerRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cardio_inner_record_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CardioInnerRecordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
