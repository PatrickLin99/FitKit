package com.patrick.fittracker.cardio

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider

import com.patrick.fittracker.R
import com.patrick.fittracker.databinding.CardioSelectionFragmentBinding
import com.patrick.fittracker.databinding.PoseSelectFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.group.GroupViewModel

class CardioSelectionFragment : Fragment() {


//    private lateinit var viewModel: CardioSelectionViewModel
    private val viewModel by viewModels <CardioSelectionViewModel> {getVmFactory()}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CardioSelectionFragmentBinding.inflate(inflater, container,false)




        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(CardioSelectionViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
