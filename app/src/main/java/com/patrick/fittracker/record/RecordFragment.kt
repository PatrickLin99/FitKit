package com.patrick.fittracker.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.patrick.fittracker.databinding.RecordFragmentBinding


class RecordFragment : Fragment() {

    companion object {
        fun newInstance() = RecordFragment()
    }

    private lateinit var viewModel: RecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.record_fragment, container, false)
        val binding = RecordFragmentBinding.inflate(inflater, container,false)

        val movement = RecordFragmentArgs.fromBundle(requireArguments()).muscleKey

        binding.recordMuscleMainTitle.text = "$movement"

        var setNum : Int = 1
        binding.setNumber.text = "第 $setNum 組"



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
