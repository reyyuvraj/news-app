package com.example.firebased.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.firebased.R

class AboutFragment : Fragment(R.layout.fragment_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.back).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_aboutFragment_to_newsFragment)
        }
    }
}