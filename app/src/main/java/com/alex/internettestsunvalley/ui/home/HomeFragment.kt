package com.alex.internettestsunvalley.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alex.internettestsunvalley.Models.Task
import com.alex.internettestsunvalley.R
import com.alex.internettestsunvalley.callBacks.TaskCallback
import com.alex.internettestsunvalley.controllers.ControllerData
import com.alex.internettestsunvalley.databinding.FragmentHomeBinding
import com.alex.internettestsunvalley.services.ConectionI

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var btnSendData: ImageButton
    private lateinit var textView: TextView
    private lateinit var leftFlagView:View
    private lateinit var rightFlagView:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textStart
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSendData = view.findViewById(R.id.btnSendData) as ImageButton
        leftFlagView=view.findViewById(R.id.leftFlagView)
        rightFlagView=view.findViewById(R.id.rightFlagView)
        textView=view.findViewById(R.id.text_start)
        btnSendData.setOnClickListener {

            btnSendData.visibility = View.GONE
            val animation = AnimationUtils.loadAnimation(view.context, R.anim.btn_scale_anim)
            btnSendData.startAnimation(animation)
            val resposeConection = ConectionI.isNetworkConnected(view.context)
            if (resposeConection) {
                sendDataToBackend(resposeConection, view)
            } else {
                viewStatus(1)
                ToastView.createNewToast(view, "NO HAY INTERNET SE INICIA A GUARDAR EN LOCAL", Color.BLACK, Color.BLUE, Color.BLACK, 2)

                saveDataLocally(view)
            }


        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendDataToBackend(resposeConection: Boolean, view: View) {
        var responseController=ControllerData.processDataForSendDataToApi(view.context,resposeConection)
        if (responseController){
            ToastView.createNewToast(view, "Internet activo", Color.BLACK, Color.BLUE, Color.GREEN, 2)
            var status="";
            ControllerData.getDataAllTaskById(view, object : TaskCallback {
                override fun onSuccess(tasks: List<Task>) {
                    for (task in tasks) {

                        status= task.estado!!
                        textView.text="Tu ultima fecha de actualizacion de verificacion de internet fue: "+task.fechaCreacion
                    }
                    if (status=="on"){
                        viewStatus(0)
                    }


                }

                override fun onFailure(error: Throwable) {
                    viewStatus(1)
                    ToastView.createNewToast(view, "Algo salio mal", Color.BLACK, Color.BLUE, Color.RED, 2)

                }
            })

        }else{
            viewStatus(1)
            ToastView.createNewToast(view, "Algo salio mal", Color.BLACK, Color.BLUE, Color.RED, 2)

        }

    }

    private fun saveDataLocally(view: View) {
        ToastView.createNewToast(view, "INTERNET INACTIVO", Color.BLACK, Color.BLUE, Color.RED , 2)
        viewStatus(1)
    }

    private fun viewStatus(stado:Int){
        if (stado==1){
            leftFlagView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.pressed_color))
            rightFlagView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))


        }else{
            leftFlagView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

            rightFlagView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.button_color))

        }
        btnSendData.visibility = View.VISIBLE


    }


}
