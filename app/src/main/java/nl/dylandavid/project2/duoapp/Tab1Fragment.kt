package nl.dylandavid.project2.duoapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Property
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import nl.dylandavid.project2.duoapp.databinding.ActivityMainBinding
import nl.dylandavid.project2.duoapp.databinding.FragmentTab1Binding
import java.util.*
import kotlin.properties.ObservableProperty




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tab1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tab1Fragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentTab1Binding

    // TODO: Rename and change types of parameters
    private var param1: String? = null

    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun count(){
        Tab1Count.count()
        Tab1Count.count.notifyChange()
    }

    fun getCount(): Int{
        return Tab1Count.count.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Tab1Count.count.addOnPropertyChangedCallback(object : androidx.databinding.Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(
                sender: androidx.databinding.Observable?,
                propertyId: Int
            ) {
                (mContext as Activity).runOnUiThread {
                    binding.CountText.setText(""+getCount())
                }
            }

        })

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTab1Binding.inflate(inflater)

        binding.CountButton.setOnClickListener{
            count()
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

    override fun onClick(view: View?) {
        when(view){
            binding.CountButton -> {
                count()
            }
        }
    }
}

object Tab1Count{
    val count: ObservableInt = ObservableInt(0)
    fun count(){
        var x = count.get()
        if(x!=null){
            count.set(x+1)
        }
    }

    fun getCount(): Int{
        return count.get()
    }
}