package com.patrick.fittracker.profile.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.patrick.fittracker.NavigationDirections
import com.patrick.fittracker.data.AddTrainingRecord
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

        var profile_height : Long = 0
        var profile_weight : Long = 0
        var profile_bodyFat : Long = 0


        binding.seekBarHeight.onProgressChangedListener = object :
            BubbleSeekBar.OnProgressChangedListener {
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
                Toast.makeText(requireContext(), "身高為$progress", Toast.LENGTH_LONG).show()
                profile_height = progress.toLong()
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
                profile_weight= progress.toLong()
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
                profile_bodyFat = progress.toLong()
            }
            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
            }
            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
            }
        }

        binding.updateInfoImage.setOnClickListener {

            viewModel.infoNameandAge()
            viewModel.addTrainingRecordd.value?.info_height = profile_height
            viewModel.addTrainingRecordd.value?.info_weight = profile_weight
            viewModel.addTrainingRecordd.value?.info_BodyFat = profile_bodyFat
            viewModel.addTrainingRecordd.value?.let { viewModel.uploadProfileInfo(it) }

            findNavController().navigate(NavigationDirections.actionGlobalProfileFragment())
        }


        return binding.root
    }

}

//private operator fun Any.invoke(onSeekBarChangeListener: BubbleSeekBar.OnProgressChangedListener) {
//
//}

