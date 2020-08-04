package com.patrick.fittracker.timer

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.patrick.fittracker.databinding.CountDownTimerFragmentBinding
import com.patrick.fittracker.ext.getVmFactory


class CountDownTimerFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels <CountDownTimerViewModel> { getVmFactory() }
    lateinit var vibrator: Vibrator
    lateinit var vibrationEffect: VibrationEffect

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CountDownTimerFragmentBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.countingClock.visibility = View.GONE


        viewModel.timeSecond.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("time2", viewModel.timeSecond.value.toString())

                viewModel.timeSecond.value?.let { it1 ->
                    binding.startCounting.setOnClickListener {
                        binding.enterTime.visibility = View.INVISIBLE
                        binding.countingClock.visibility = View.VISIBLE
                        binding.startCounting.visibility = View.GONE

                        object : CountDownTimer(it1*1000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
//                                binding.countingClock.setText("seconds remaining: " + millisUntilFinished / 1000)
                                binding.countingClock.text = "${millisUntilFinished.div(1000)}"

                            }

                            override fun onFinish() {
                                binding.countingClock.text = "完成"
                                vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                                vibrationEffect = VibrationEffect.createOneShot(3000, 250)
                                vibrator.vibrate(vibrationEffect)

//                                val ringtone: Ringtone
//                                val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//                                val r = RingtoneManager.getRingtone(getApplicationContext(), notification)
//                                r.play()

                            }
                        }.start()


                    }
                }


            }
        })

        binding.dismissButton.setOnClickListener {
            dismiss()
        }


        return binding.root
    }
}
