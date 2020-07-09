package com.patrick.fittracker.profile.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.patrick.fittracker.databinding.EditProfileFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.xw.repo.BubbleSeekBar


class EditProfileFragment : Fragment() {

    private val viewModel by viewModels<EditProfileViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = EditProfileFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

//        binding.seekBarHeight.onProgressChangedListener(object : SeekBar.OnSeekBarChangeListener {
//
//            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//
//            }
//            override fun onStopTrackingTouch(p0: SeekBar?) {
//
//            }
//        })


        binding.seekBarHeight.onProgressChangedListener = object :
            BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
                Toast.makeText(requireContext(), "身高為$progress", Toast.LENGTH_LONG).show()
            }
            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
            }
            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
            }
        }

        binding.seekBarWeight.onProgressChangedListener = object :
            BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
                Toast.makeText(requireContext(), "體重為$progress", Toast.LENGTH_LONG).show()
            }
            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
            }
            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
            }
        }

        binding.seekBarBodyFat.onProgressChangedListener = object :
            BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
                Toast.makeText(requireContext(), "體脂肪為$progress", Toast.LENGTH_LONG).show()
            }
            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
            }
            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
            }
        }






        return binding.root
    }

}

//private operator fun Any.invoke(onSeekBarChangeListener: BubbleSeekBar.OnProgressChangedListener) {
//
//}

