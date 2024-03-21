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
    private lateinit var btnSendData: ImageButton // Cambio el tipo de Button a ImageButton
    private lateinit var textView: TextView // Cambio el tipo de Button a ImageButton

    private lateinit var loadingProgressBar:ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSendData = view.findViewById(R.id.btnSendData) as ImageButton // Cambio de Button a ImageButton
        textView=view.findViewById(R.id.text_home)
        loadingProgressBar=view.findViewById(R.id.loadingProgressBar)
        btnSendData.setOnClickListener {
            btnSendData.visibility = View.GONE
            val animation = AnimationUtils.loadAnimation(view.context, R.anim.btn_scale_anim)
            btnSendData.startAnimation(animation)
            // Mostrar el ProgressBar
            loadingProgressBar.visibility = View.VISIBLE
            val resposeConection = ConectionI.isNetworkConnected(view.context)
            if (resposeConection) {
                sendDataToBackend(resposeConection, view)
            } else {
                ToastView.createNewToast(view, "NO HAY INTERNET SE INICIA A GUARDAR EN LOCAL", Color.WHITE, Color.BLUE, Color.BLACK, 2)

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
            ToastView.createNewToast(view, "Datos enviados a la nube", Color.BLACK, Color.BLUE, Color.BLACK, 10)

            ControllerData.getDataAllTaskById(view, object : TaskCallback {
                override fun onSuccess(tasks: List<Task>) {
                    for (task in tasks) {
                        println("ID: ${task.id}")
                        println("Título: ${task.title}")
                        println("Descripción: ${task.description}")
                        println("Fecha de creación: ${task.fechaCreacion}")
                        println("Estado: ${task.estado}")
                        println("--------------------------------------")
                        textView.text="Tu ultima fecha de actualizacion de verificacion de internet fue: "+task.fechaCreacion
                    }

                    btnSendData.visibility = View.VISIBLE
                    loadingProgressBar.visibility = View.GONE

                }

                override fun onFailure(error: Throwable) {
                    ToastView.createNewToast(view, "Algo salio mal", Color.WHITE, Color.BLUE, Color.BLACK, 2)

                }
            })

        }else{
            ToastView.createNewToast(view, "Algo salio mal", Color.WHITE, Color.BLUE, Color.BLACK, 2)

        }

    }

    private fun saveDataLocally(view: View) {
        ToastView.createNewToast(view, "Mensaje de ejemplo", Color.WHITE, Color.BLUE, Color.BLACK, 2)
    }


}
