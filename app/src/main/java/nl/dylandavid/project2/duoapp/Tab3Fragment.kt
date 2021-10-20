package nl.dylandavid.project2.duoapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.FloatMath
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.core.content.ContextCompat.getSystemService
import nl.dylandavid.project2.duoapp.databinding.FragmentTab3Binding
import kotlin.math.abs
import kotlin.math.sqrt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tab3Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tab3Fragment : Fragment(), SensorEventListener {
    private lateinit var binding: FragmentTab3Binding

    // Start with some variables
    private lateinit var sensorMan: SensorManager
    private lateinit var accelerometer: Sensor

    private lateinit var mGravity: FloatArray
    private var mAccel = 0f
    private var mAccelCurrent = 0f
    private var mAccelLast = 0f

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        var sm = mContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if(sm!=null){
            sensorMan = sm
        }

        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onPause() {
        super.onPause()
        sensorMan.unregisterListener(this);
    }

    override fun onResume() {
        super.onResume()
        sensorMan.registerListener(this, accelerometer,
            SensorManager.SENSOR_DELAY_UI)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTab3Binding.inflate(inflater)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tab3Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tab3Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private var sensorChanged: Boolean = false
    private val second: Long = 1000000000
    private var lastChange: Long = 0

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.getType() === Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values
            // Shake detection
            val x = mGravity[0]
            val y = mGravity[1]
            val z = mGravity[2]
            mAccelLast = mAccelCurrent
            mAccelCurrent = sqrt(x * x + y * y + z * z)
            val delta = mAccelCurrent - mAccelLast
            mAccel = mAccel * 0.99f + delta

            if (mAccel > 2) {
                if(!sensorChanged){
                    lastChange = System.nanoTime()
                    binding.root.setBackgroundColor(Color.parseColor("#fc8403"))
                    sensorChanged = true
                }
            } else {
                sensorChanged = false
                if(abs(lastChange - System.nanoTime()) > second*10){
                    binding.root.setBackgroundColor(Color.BLUE)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}