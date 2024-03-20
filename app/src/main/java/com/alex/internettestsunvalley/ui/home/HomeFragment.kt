package com.alex.internettestsunvalley.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alex.internettestsunvalley.Models.Task
import com.alex.internettestsunvalley.R
import com.alex.internettestsunvalley.controllers.ControllerData
import com.alex.internettestsunvalley.databinding.FragmentHomeBinding
import com.alex.internettestsunvalley.services.ApiClient
import com.alex.internettestsunvalley.services.ConectionI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var btnSendData: ImageButton // Cambio el tipo de Button a ImageButton
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
        loadingProgressBar=view.findViewById(R.id.loadingProgressBar)
        btnSendData.setOnClickListener {
            btnSendData.visibility = View.GONE

            // Mostrar el ProgressBar
            loadingProgressBar.visibility = View.VISIBLE
            val resposeConection = ConectionI.isNetworkConnected(view.context)
            if (resposeConection) {
                sendDataToBackend(resposeConection, view)
            } else {
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
            Toast.makeText(view.context, "Enviando datos al backend...s"+resposeConection, Toast.LENGTH_SHORT).show()
            btnSendData.visibility = View.VISIBLE
            loadingProgressBar.visibility = View.GONE
            ControllerData.getDataAllTaskById(view)

        }

    }

    private fun saveDataLocally(view: View) {
        Toast.makeText(view.context, "Guardando datos localmente...", Toast.LENGTH_SHORT).show()
    }


}
