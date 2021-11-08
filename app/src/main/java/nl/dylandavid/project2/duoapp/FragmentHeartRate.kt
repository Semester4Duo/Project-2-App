package nl.dylandavid.project2.duoapp

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Camera
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.DialogFragment
import nl.dylandavid.project2.duoapp.data.BmiViewModel
import nl.dylandavid.project2.duoapp.data.BpViewModel
import nl.dylandavid.project2.duoapp.databinding.FragmentBmiBinding
import nl.dylandavid.project2.duoapp.databinding.FragmentHeartRateBinding
import android.hardware.camera2.CameraAccessException

import androidx.core.content.ContextCompat.getSystemService

import android.os.Build
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.PermissionListener
import java.util.jar.Manifest


class FragmentHeartRate : DialogFragment() {


    private lateinit var mBpViewModel: BpViewModel
    private lateinit var cameraManager: CameraManager

    private var _binding: FragmentHeartRateBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHeartRateBinding.inflate(inflater, container, false)
        var videoView = binding.HeartRatevideoView
        var videoPath = "android.resource://"+activity?.packageName +"/"+R.raw.heartratevid
        videoView.setVideoURI(Uri.parse(videoPath))
        videoView.start()
        cameraManager = activity?.getSystemService(Context.CAMERA_SERVICE)as CameraManager
        val cameraListId = cameraManager.cameraIdList[0]
        val hasFlash: Boolean? =
            activity?.getPackageManager()?.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        cameraManager.getCameraCharacteristics(cameraListId).physicalCameraIds

          //  cameraManager.setTorchMode(cameraListId, true)


        binding.btnCancelHR.setOnClickListener {

         //   cameraManager.setTorchMode(cameraListId, false)

            dialog!!.dismiss()
        }
        binding.btnSaveHR.setOnClickListener {

         //   cameraManager.setTorchMode(cameraListId, false)

            dialog!!.dismiss()
        }
        return binding.root
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


}