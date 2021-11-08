package nl.dylandavid.project2.duoapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import nl.dylandavid.project2.duoapp.data.Bmi
import nl.dylandavid.project2.duoapp.data.BmiViewModel
import nl.dylandavid.project2.duoapp.databinding.FragmentBmiBinding
import java.text.SimpleDateFormat
import java.util.*


class FragmentBmi : DialogFragment() {

    private lateinit var mBmiViewModel: BmiViewModel

    private var _binding: FragmentBmiBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBmiBinding.inflate(inflater, container, false)
        binding.btnCancelBmi.setOnClickListener{
            dialog!!.dismiss()
        }
        mBmiViewModel = ViewModelProvider(this).get(BmiViewModel::class.java)
        binding.btnSaveBMI.setOnClickListener{
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
        var length: Double = 1.81
        val weight = binding.editTextWeight.text.toString()
        val wc = binding.editTextWaist.text.toString()
        var bmiVal = String.format("%.1f",weight.toDouble()/(length*length))

        if (inputCheck(weight, wc)){
            mBmiViewModel.readBmi.observe(viewLifecycleOwner, Observer { bmi ->
                if (bmi == null){
                    val bmiNew = Bmi(0,bmiVal.toString(), weight, wc, currentDate.toString())
                    mBmiViewModel.addBmi(bmiNew)
                }else{
                    val updateBmi = Bmi(1, bmiVal.toString(), weight, wc, currentDate.toString())
                    mBmiViewModel.updateBmi(updateBmi)
                }
            })
            Toast.makeText(requireContext(), "Succesfully updated", Toast.LENGTH_LONG).show()
            dialog!!.dismiss()
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(weight:String, wc:String):Boolean{
        return !(TextUtils.isEmpty(weight) && TextUtils.isEmpty(wc))
    }

}