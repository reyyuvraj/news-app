package com.example.firebased.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            binding.in007.setOnClickListener {
                val email: String =
                    binding.in004.text.toString()
                val password: String =
                    binding.in006.text.toString()

                if (checkEmail(email) && checkPassword(password)) {
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
                            }
                        }
                } else {
                    Toast.makeText(
                        this.context,
                        "Credentials incorrect, Sign up.",
                        Toast.LENGTH_SHORT
                    ).show()
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

/*class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var auth: FirebaseAuth
    //private lateinit var binding: FragmentSignInBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_newsFragment)
        } else {
            view.findViewById<Button>(R.id.in007).setOnClickListener {
                val email: String =
                    view.findViewById<AppCompatEditText>(R.id.in004).text.toString().trim()
                val password: String =
                    view.findViewById<AppCompatEditText>(R.id.in006).text.toString().trim()

                if(checkEmail(email) && validatePass(password)){
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Navigation.findNavController(view)
                                .navigate(R.id.action_signInFragment_to_newsFragment)
                        } else {
                            Toast.makeText(
                                this.context,
                                "Authentication Error! Sign up required.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(this.context,"Credentials empty!",Toast.LENGTH_SHORT).show()
                }
            }
        }

        view.findViewById<TextView>(R.id.to_sign_up).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun validatePass(pass: String): Boolean {
        var regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,20}$"
        var p: Pattern = Pattern.compile(regex)
        if (pass.isEmpty()) return false
        var m = p.matcher(pass)
        return m.matches()
    }


    private fun checkEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
}*/