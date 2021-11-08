package nl.dylandavid.project2.duoapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import nl.dylandavid.project2.duoapp.data.BloodPressure
import nl.dylandavid.project2.duoapp.data.Bmi
import nl.dylandavid.project2.duoapp.data.BmiViewModel
import nl.dylandavid.project2.duoapp.data.BpViewModel
import nl.dylandavid.project2.duoapp.databinding.FragmentBmiBinding
import nl.dylandavid.project2.duoapp.databinding.FragmentTab2Binding
import java.lang.Math.round

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tab2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tab2Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentTab2Binding? = null
    val binding get() = _binding!!

    private var itemListBlood: MutableList<ProfileListItem> = mutableListOf<ProfileListItem>(
        ProfileListItem("Natrium","145","mmol/l", null),
        ProfileListItem("Potassium","4","mmol/l", null),
        ProfileListItem("Creatine","99","mmol/l", null),
        ProfileListItem("Thyroid function",null,null, mutableListOf<ProfileListChildItem>(
            ProfileListChildItem("FT4","19,4","pmol/l"),
            ProfileListChildItem("FT3","4,9","pmol/l"),
            ProfileListChildItem("TSH","1,3","mU/l")
        )),
        ProfileListItem("Cholesterol",null,null, mutableListOf<ProfileListChildItem>(
            ProfileListChildItem("Total","4,6","mmol/l"),
            ProfileListChildItem("HDL","1,1","mmol/l"),
            ProfileListChildItem("LDL","2,6","mmol/l"),
            ProfileListChildItem("Triglycerides","1,7","mmol/l"),
            ProfileListChildItem("Ratio","4","")
        )),
        ProfileListItem("Glucose","6,1","mmol/l", null)
    )
    private var itemListUrine: MutableList<ProfileListItem> = mutableListOf<ProfileListItem>(
        ProfileListItem("Creatine","145","mmol/l", null),
        ProfileListItem("Albumine","99","mmol/l", null),
    )

    lateinit var profileExpandableListView: NonScrollExpandableListView
    lateinit var profileExpandableListViewUrine: NonScrollExpandableListView
    lateinit var expandableListAdapter: CustomExpandableListAdapter
    lateinit var expandableListAdapterUrine: CustomExpandableListAdapter

    private lateinit var mBmiViewModel: BmiViewModel
    private lateinit var mBpViewModel: BpViewModel

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
        _binding = FragmentTab2Binding.inflate(inflater, container, false)
        mBpViewModel = ViewModelProvider(this).get(BpViewModel::class.java)

        mBpViewModel.readBp.observe(viewLifecycleOwner, Observer {bp ->
            if(bp != null) {
                binding.tvBPVal.text = bp.bpUp + "/" + bp.bpLow
                binding.tvBPUpdate.text = "Last update " +bp.lastUpdate
                binding.tvHRVal.text = bp.heartRate
                binding.tvPBUpdate.text = "Last update " +bp.lastUpdateHeartRate
            }else{
                binding.tvBPVal.text = "170/108"
                binding.tvBPUpdate.text = "Last update 12-09-2021"
                binding.tvHRVal.text = "66"
                binding.tvPBUpdate.text = "Last update 12-09-2021"
            }

        })

        mBmiViewModel = ViewModelProvider(this).get(BmiViewModel::class.java)
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

        profileExpandableListView = binding.listBloodExamination
        expandableListAdapter = CustomExpandableListAdapter(requireActivity(), itemListBlood)
        profileExpandableListView.setAdapter(expandableListAdapter)

        profileExpandableListViewUrine = binding.listUrineExamination
        expandableListAdapterUrine = CustomExpandableListAdapter(requireActivity(), itemListUrine)
        profileExpandableListViewUrine.setAdapter(expandableListAdapterUrine)

        binding.btnMeasureBMI.setOnClickListener{
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
         * @return A new instance of fragment Tab2Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tab2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}