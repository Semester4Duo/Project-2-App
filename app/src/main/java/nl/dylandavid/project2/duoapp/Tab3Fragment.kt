package nl.dylandavid.project2.duoapp

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_tab3.view.*
import kotlinx.android.synthetic.main.fragment_tab3.view.imageButton2
import nl.dylandavid.project2.duoapp.LocalService.LocalBinder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tab3Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tab3Fragment : Fragment() {
    private var currentView: View? = null
    private lateinit var mContext: Context
    private var mService: LocalService? = null
    private var bound: Boolean = false
    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            val mLocalBinder = service as LocalBinder
            mService = mLocalBinder.service
            mService!!.setOnConnect{
                requireActivity().runOnUiThread{
                    currentView!!.hostcallButton.visibility = View.VISIBLE
                    currentView!!.hostcallButton.invalidate()
                    currentView!!.hostcallButton.requestLayout()
                    currentView!!.refreshDrawableState()
                }
            }
            mService!!.setOnDisconnect{
                requireActivity().runOnUiThread{
                    currentView!!.hostcallButton.visibility = View.GONE
                    currentView!!.hostcallButton.invalidate()
                    currentView!!.hostcallButton.requestLayout()
                    currentView!!.refreshDrawableState()
                }
            }

            mService!!.setOnNewConference{ uuid: String ->
                requireActivity().runOnUiThread{
                    conferenceId = uuid
                    currentView!!.hostcallButton.text = "Join"
                    currentView!!.hostcallButton.setBackgroundResource(R.drawable.button_large_green)
                    currentView!!.hostcallButton.invalidate()
                    currentView!!.hostcallButton.requestLayout()
                    currentView!!.refreshDrawableState()
                }
            }

            mService!!.setOnEndConference{ uuid: String ->
                if(uuid==conferenceId){
                    requireActivity().runOnUiThread{
                        conferenceId = ""
                        currentView!!.hostcallButton.text = "Host"
                        currentView!!.hostcallButton.setBackgroundResource(R.drawable.button_large)
                        currentView!!.hostcallButton.invalidate()
                        currentView!!.hostcallButton.requestLayout()
                        currentView!!.refreshDrawableState()
                    }
                }
            }
            bound = true
        }

        override fun onServiceDisconnected(className: ComponentName) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null
            bound = false
        }
    }



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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private var conferenceId: String = ""

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_tab3, container, false)
        currentView!!.imageButton2.clipToOutline = true
        currentView!!.imageButton2.setImageBitmap(getBitmapFromResources(resources, R.drawable.doctor5))

        currentView!!.noteViewPager.adapter = NoteAdapter(parentFragmentManager, lifecycle)
        currentView!!.noteViewPager.currentItem = 6

        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val now: LocalDateTime = LocalDateTime.now()

        currentView!!.dateText.text = dtf.format(now)

        currentView!!.noteViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                if(position==20){
                    currentView!!.dateText.text = dtf.format(now)
                }

                if(position<20){
                    currentView!!.dateText.text = dtf.format(now.plusDays(position-6L))
                }

                if(position>20){
                    currentView!!.dateText.text = dtf.format(now.minusDays(position-6L))
                }
            }
        })

        currentView!!.left_chevron.setOnClickListener {
            currentView!!.noteViewPager.currentItem = currentView!!.noteViewPager.currentItem - 1
        }
        currentView!!.right_chevron.setOnClickListener {
            currentView!!.noteViewPager.currentItem = currentView!!.noteViewPager.currentItem + 1
        }

        currentView!!.hostcallButton.setOnClickListener{
            if(conferenceId.isNullOrEmpty()){
                val uuid = UUID.randomUUID()
                //Call SignalR stuff
                call(uuid.toString(), false)
            } else {
                call(conferenceId, true)
            }
        }

        val mIntent = Intent(mContext, LocalService::class.java)
        mContext.bindService(mIntent, mConnection, BIND_AUTO_CREATE)
        return currentView;
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

    private fun call(conferenceId: String, isJoin: Boolean){
        val intent = Intent(mContext, RTCActivity::class.java)
        intent.putExtra("meetingID", conferenceId)
        intent.putExtra("isJoin",isJoin)
        if(isJoin){
            mService!!.joinCall(conferenceId)
        } else {
            mService!!.hostCall(conferenceId)
        }
        startActivity(intent)
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
}

