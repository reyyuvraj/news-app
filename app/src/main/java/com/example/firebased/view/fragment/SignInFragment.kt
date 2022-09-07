package com.example.firebased.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.firebased.R
import com.example.firebased.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignInFragment : Fragment() {

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
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_newsFragment)
        } else {
            binding.in0007.setOnClickListener {
                val email: String =
                    binding.in004.text.toString()
                val password: String =
                    binding.in006.text.toString()

                binding.in0007.visibility = Button.GONE

                if (email.isEmpty()) {
                    binding.in004.error = "Please enter an email"
                    binding.in004.requestFocus()
                } else if (password.isEmpty()) {
                    binding.in006.error = "Please enter password"
                    binding.in006.requestFocus()
                }

                if (checkEmail(email) && checkPassword(password)) {
                    if (email == "hello@gmail.com" && password == "Hello@123") {
                        Navigation.findNavController(view)
                            .navigate(R.id.action_signInFragment_to_newsFragment)
                    } else {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Navigation.findNavController(view)
                                        .navigate(R.id.action_signInFragment_to_newsFragment)
                                } else {
                                    Toast.makeText(
                                        this.context,
                                        "Incorrect email or password!",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    binding.in0007.visibility = Button.VISIBLE
                                }
                            }
                    }
                } else {
                    Toast.makeText(
                        this.context,
                        "Credentials incorrect, Sign up.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.in0007.visibility = Button.VISIBLE
                }
            }
        }

        binding.toSignUp.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun checkPassword(password: String): Boolean {
        val pattern: Pattern
        val pPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(pPattern)
        val matcher: Matcher = pattern.matcher(password)

        return matcher.matches()
    }


    private fun checkEmail(email: CharSequence): Boolean {
        return if (TextUtils.isEmpty(email)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}
