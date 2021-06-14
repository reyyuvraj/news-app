package com.example.firebased.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.firebased.R

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.in007).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_newsFragment)
        }

        view.findViewById<TextView>(R.id.to_sign_up).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }
}

/*
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignInBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(view)

        if (auth.currentUser != null) {
            navController.navigate(R.id.action_signInFragment_to_newsFragment)
        } else {
            binding.in007.setOnClickListener {
                val email: String = binding.in004.text.toString().trim()
                val password: String = binding.in006.text.toString().trim()
                //Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_newsFragment)

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.navigate(R.id.action_signInFragment_to_newsFragment)
                    } else {
                        Toast.makeText(this.context, "Authentication Error!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        binding.toSignUp.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }
}*/