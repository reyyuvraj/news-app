package com.example.firebased

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.firebased.databinding.FragmentSignUpBinding
import com.example.firebased.model.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth
    private lateinit var rootNode: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
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
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.getReference("Users")

        binding.up015.setOnClickListener {

            val name: String = binding.up0004.text.toString().trim()
            val email: String = binding.up004.text.toString().trim()
            val password: String = binding.up006.text.toString().trim()
            val cPassword: String = binding.up008.text.toString().trim()
            val age: String = binding.up01.text.toString().trim()
            val phone: String = binding.up012.text.toString().trim()
            val bio: String = binding.up014.text.toString().trim()
            var userId: String = ""

            if (email.isEmpty() || password.isEmpty() || cPassword.isEmpty()) {
                Toast.makeText(this.context, "Email and password is mandatory.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (email.isNotEmpty()) {
                    userId = email.substring(0, email.indexOf('@'))
                }
                if (!validatePass(password))
                    Toast.makeText(
                        this.context,
                        "Password must contain a digit, a capital letter and should be of length 8-20.",
                        Toast.LENGTH_LONG
                    ).show()
                else {
                    if (password.compareTo(cPassword) == 0) {
                        val user = UserDetails(name, email, password, cPassword, age, phone, bio)
                        reference.child(userId).setValue(user).addOnCompleteListener {
                            if (it.isSuccessful) {
                                auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            navController.navigate(R.id.action_signUpFragment_to_signInFragment)
                                        } else {
                                            val exc: String = task.exception.toString()
                                            val ind: Int = exc.indexOf(':') + 2
                                            Toast.makeText(
                                                this.context,
                                                exc.substring(ind),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Log.d("error", "error in creation", task.exception)
                                        }
                                    }
                            }
                        }
                    } else {
                        Toast.makeText(
                            this.context,
                            "Passwords doesn't match! Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        binding.toSignIn.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }
    private fun validatePass(pass: String): Boolean {
        var regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,20}$"
        var p: Pattern = Pattern.compile(regex)
        if (pass.isEmpty()) return false
        var m = p.matcher(pass)
        return m.matches()
    }
}