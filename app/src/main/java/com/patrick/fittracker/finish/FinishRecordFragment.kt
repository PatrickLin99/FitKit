package com.patrick.fittracker.finish

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.patrick.fittracker.R

class FinishRecordFragment : Fragment() {

    companion object {
        fun newInstance() = FinishRecordFragment()
    }

    private lateinit var viewModel: FinishRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.finish_record_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FinishRecordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
