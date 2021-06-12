package com.example.firebased

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.in007).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_newsFragment)
        }

        view.findViewById<TextView>(R.id.to_sign_up).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }
}