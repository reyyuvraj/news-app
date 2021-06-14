package com.example.firebased.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.firebased.R

class DetailsFragment : Fragment(R.layout.fragment_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.ls0002).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_detailsFragment_to_newsFragment)
        }
    }
}