package com.example.firebased

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.up015).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        view.findViewById<TextView>(R.id.to_sign_in).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }
}