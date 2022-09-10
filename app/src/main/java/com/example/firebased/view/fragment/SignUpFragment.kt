package com.example.firebased.view.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.firebased.R
import com.example.firebased.databinding.FragmentSignUpBinding
import com.example.firebased.model.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignUpBinding
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
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

        view.findViewById<Button>(R.id.up0015).setOnClickListener {
            val name: String =
                binding.up0004.text.toString()
            val email: String =
                binding.up004.text.toString()
            val password: String =
                binding.up006.text.toString()
            val cPassword: String =
                binding.up008.text.toString()
            val age: String =
                binding.up01.text.toString()
            val phone: String =
                binding.up012.text.toString()
            val bio: String =
                binding.up014.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || cPassword.isEmpty() || age.isEmpty() || phone.isEmpty() || bio.isEmpty()) {
                Toast.makeText(
                    this.context,
                    "Please enter all of the credentials.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this.context, "Please enter valid email", Toast.LENGTH_SHORT).show()
            } else if (!checkPassword(password)) {
                Toast.makeText(
                    this.context,
                    "Enter a password of length 8 or greater, an uppercase char and a numeric digit must be included.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (age.toInt() > 120 || age.toInt() == 0) {
                Toast.makeText(this.context, "Please enter valid age", Toast.LENGTH_SHORT)
                    .show()
            } else if (phone.length != 10) {
                Toast.makeText(this.context, "Please enter valid phone number", Toast.LENGTH_SHORT)
                    .show()
            } else if (password.compareTo(cPassword) == 0) {

                binding.up0015.visibility = Button.GONE

                val userDetail = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "age" to age,
                    "phone" to phone,
                    "bio" to bio
                )

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("signup", "onViewCreated: Account created")
                            val uId = auth.currentUser?.uid

                            val reference = hashMapOf(
                                "uid" to uId
                            )

                            if (uId != null) {
                                db.collection("users")
                                    .add(userDetail)
                                    .addOnSuccessListener { documentReference ->
                                        Log.d(
                                            "db",
                                            "snapshot added with ID: ${documentReference.id}"
                                        )
                                        Navigation.findNavController(view)
                                            .navigate(R.id.action_signUpFragment_to_newsFragment)
                                    }
                                    .addOnFailureListener { e ->
                                        Log.d("db", "Error adding document", e)
                                        Toast.makeText(
                                            this.context,
                                            "Sign up failed.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        binding.up0015.visibility = Button.VISIBLE
                                    }
                                db.collection(uId).add(userDetail)
                            }
                        }
                    }.addOnFailureListener {
                        Toast.makeText(
                            this.context,
                            "Sign up failed", Toast.LENGTH_SHORT
                        ).show()
                    }

            } else {
                Toast.makeText(
                    this.context,
                    "Passwords do not match.",
                    Toast.LENGTH_SHORT
                ).show()
                binding.up0015.visibility = Button.VISIBLE
            }
        }

        view.findViewById<TextView>(R.id.to_sign_in).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun checkPassword(password: String): Boolean {
        val pattern: Pattern
        val pPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(pPattern)
        val matcher: Matcher = pattern.matcher(password)

        return matcher.matches()
    }
}