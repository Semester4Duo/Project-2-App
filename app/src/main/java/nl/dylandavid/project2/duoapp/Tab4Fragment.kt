package nl.dylandavid.project2.duoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tab4.view.*
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import kotlinx.android.synthetic.main.fragment_tab4.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tab1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tab4Fragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_tab4, container, false)
        // Inflate the layout for this fragment

        view.healthy_image.setImageBitmap(getBitmapFromResources(resources, R.mipmap.gezond_eten));
        view.exercise_image.setImageBitmap(getBitmapFromResources(resources, R.mipmap.bewegen));
        view.relax_image.setImageBitmap(getBitmapFromResources(resources, R.mipmap.ontspannen));

        view.healthy_button.setOnClickListener {
            val uri: Uri =
                Uri.parse("https://www.hartstichting.nl/gezond-leven/gezond-eten") // missing 'http://' will cause crashed

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        view.exercise_button.setOnClickListener {
            val uri: Uri =
                Uri.parse("https://www.hartstichting.nl/gezond-leven/beweging") // missing 'http://' will cause crashed

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        view.relax_button.setOnClickListener {
            val uri: Uri =
                Uri.parse("https://www.hartstichting.nl/risicofactoren/stress/minder-stress-in-je-leven") // missing 'http://' will cause crashed

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        return view
    }

    fun getBitmapFromResources(resources: Resources?, resImage: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = false
        options.inDither = false
        options.inSampleSize = 1
        options.inScaled = false
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