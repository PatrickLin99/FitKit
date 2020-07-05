package com.patrick.fittracker.addsuccess

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider

import com.patrick.fittracker.R

class AddSuccessFragment : DialogFragment() {

    companion object {
        fun newInstance() = AddSuccessFragment()
    }

    private lateinit var viewModel: AddSuccessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_success_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddSuccessViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
