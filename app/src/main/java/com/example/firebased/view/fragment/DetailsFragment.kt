package com.example.firebased.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.firebased.R
import com.example.firebased.databinding.FragmentDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        view.findViewById<Button>(R.id.ls013).setOnClickListener {
            auth.signOut()
            Navigation.findNavController(view).navigate(R.id.action_detailsFragment_to_signInFragment)
        }
    }
}