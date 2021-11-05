package nl.dylandavid.project2.duoapp

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tab1.view.*
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
        val view = inflater.inflate(R.layout.fragment_tab1, container, false)
        // Inflate the layout for this fragment
        _binding = FragmentTab1Binding.inflate(inflater, container, false)
        mBmiViewModel = ViewModelProvider(this).get(BmiViewModel::class.java)
        mBpViewModel = ViewModelProvider(this).get(BpViewModel::class.java)

        _binding.imageButton22.setImageBitmap(getBitmapFromResources(resources, R.drawable.doctor5))
        _binding.imageButton2.setImageBitmap(getBitmapFromResources(resources, R.drawable.doctor5))

        return view;
    }

    fun getBitmapFromResources(resources: Resources?, resImage: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = false
        options.inDither = false
        options.inSampleSize = 1
        options.inScaled = false
        options.outHeight = 128
        options.outWidth= 128
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeResource(resources, resImage, options)
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