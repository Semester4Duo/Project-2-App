package nl.dylandavid.project2.duoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import nl.dylandavid.project2.duoapp.data.Bmi
import nl.dylandavid.project2.duoapp.data.BmiViewModel
import nl.dylandavid.project2.duoapp.data.BpViewModel
import nl.dylandavid.project2.duoapp.databinding.FragmentTab1Binding
import nl.dylandavid.project2.duoapp.databinding.FragmentTab2Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [Tab1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tab1Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentTab1Binding? = null
    private lateinit var mBmiViewModel: BmiViewModel
    private lateinit var mBpViewModel: BpViewModel

    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTab1Binding.inflate(inflater, container, false)
        mBmiViewModel = ViewModelProvider(this).get(BmiViewModel::class.java)
        mBpViewModel = ViewModelProvider(this).get(BpViewModel::class.java)

        mBmiViewModel.readBmi.observe(viewLifecycleOwner, Observer {bmi ->
            if(bmi != null) {
                binding.tvBMIVal.text = bmi.bmi
                binding.tvBMIUpdate.text = "Last update " + bmi.lastUpdate
                binding.tvWeightVal.text = bmi.weight + "kg"
                binding.tvWCVal.text = bmi.wc + "cm"
            }else{
                binding.tvBMIVal.text = "24.2"
                binding.tvBMIUpdate.text = "Last update 12-09-2021"
                binding.tvWeightVal.text = "80kg"
                binding.tvWCVal.text = "80cm"
            }
        })

        mBpViewModel.readBp.observe(viewLifecycleOwner, Observer {bp ->
            if(bp != null) {
                binding.tvBPVal.text = bp.bpUp + "/" + bp.bpLow
                binding.tvBPUpdate.text = "Last update " +bp.lastUpdate
                binding.tvHRVal.text = bp.heartRate
                binding.tvHRUpdate.text = "Last update " +bp.lastUpdateHeartRate
            }else{
                binding.tvBPVal.text = "170/108"
                binding.tvBPUpdate.text = "Last update 12-09-2021"
                binding.tvHRVal.text = "66"
                binding.tvHRUpdate.text = "Last update 12-09-2021"
            }

        })
        binding.btnMeasureBMI.setOnClickListener {
            var dialog = FragmentBmi()
            dialog.show(parentFragmentManager, ("FragmentBmi"))
        }
        binding.btnMeasureBP.setOnClickListener{
            var dialog = FragmentBloodPressure()
            dialog.show(parentFragmentManager, ("FragmentBloodPressure"))

        }
        binding.btnMeasureHR.setOnClickListener {
            var dialog = FragmentHeartRate()
            dialog.show(parentFragmentManager, ("FragmentHeartRate"))
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tab1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tab1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}