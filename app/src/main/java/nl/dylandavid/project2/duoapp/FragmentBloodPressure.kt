package nl.dylandavid.project2.duoapp

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import nl.dylandavid.project2.duoapp.data.BloodPressure
import nl.dylandavid.project2.duoapp.data.Bmi
import nl.dylandavid.project2.duoapp.data.BmiViewModel
import nl.dylandavid.project2.duoapp.data.BpViewModel
import nl.dylandavid.project2.duoapp.databinding.FragmentBloodPressureBinding
import nl.dylandavid.project2.duoapp.databinding.FragmentBmiBinding
import java.text.SimpleDateFormat
import java.util.*


class FragmentBloodPressure : DialogFragment() {
    private lateinit var mBpViewModel: BpViewModel

    private var _binding: FragmentBloodPressureBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBloodPressureBinding.inflate(inflater, container, false)
        mBpViewModel = ViewModelProvider(this).get(BpViewModel::class.java)
        binding.btnCancelBloodPressure.setOnClickListener {
            dialog!!.dismiss()
        }
        binding.btnSaveBloodPressure.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }

    private fun insertDataToDatabase(){
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        val bpUp = binding.editTextUpperPressure.text.toString()
        val bpLow = binding.editTextLowerPressure.text.toString()
        var heartRate = binding.editTextHeartRate.text.toString()

        if (inputCheck(bpUp, bpLow, heartRate)){
            mBpViewModel.readBp.observe(viewLifecycleOwner, Observer { bp ->
                if (bp == null) {
                    val bp = BloodPressure(0,bpUp,bpLow,heartRate,currentDate,currentDate)
                    mBpViewModel.addBp(bp)
                }else {
                    val updateBp = BloodPressure(1, bpUp, bpLow, heartRate, currentDate,currentDate)
                    mBpViewModel.updateBp(updateBp)
                }
            })
            Toast.makeText(requireContext(), "Succesfully updated", Toast.LENGTH_LONG).show()
            dialog!!.dismiss()
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(bpHigh:String, bpLow:String, hr:String):Boolean{
        return !(TextUtils.isEmpty(bpHigh) && TextUtils.isEmpty(bpLow)&& TextUtils.isEmpty(hr))
    }

}