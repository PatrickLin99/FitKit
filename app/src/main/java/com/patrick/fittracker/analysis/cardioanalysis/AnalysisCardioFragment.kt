package com.patrick.fittracker.analysis.cardioanalysis

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.patrick.fittracker.R

class AnalysisCardioFragment : Fragment() {

    companion object {
        fun newInstance() = AnalysisCardioFragment()
    }

    private lateinit var viewModel: AnalysisCardioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.analysis_cardio_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AnalysisCardioViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
