package com.example.firebased.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.firebased.R
import com.example.firebased.databinding.FragmentSignUpBinding
import com.example.firebased.model.data.UserDetails
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignUpBinding

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

        view.findViewById<Button>(R.id.up015).setOnClickListener {
            signUpUser()
        }

        view.findViewById<TextView>(R.id.to_sign_in).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun signUpUser() {
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

        /*if (email.isEmpty()) {
            Toast.makeText(this.context, "Please enter email", Toast.LENGTH_SHORT).show()
        }

        if (password.isEmpty()) {
            Toast.makeText(this.context, "Please enter password", Toast.LENGTH_SHORT).show()
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this.context, "Please enter valid email", Toast.LENGTH_SHORT).show()
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Sign up successful.", Toast.LENGTH_SHORT).show()
                    Toast.makeText(
                        this.context,
                        "Automatic sign in. Going to news.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this.context, "Sign up failed.", Toast.LENGTH_SHORT).show()
                }
            }*/

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || cPassword.isEmpty() || age.isEmpty() || phone.isEmpty() || bio.isEmpty()) {
            Toast.makeText(this.context, "Please enter all of the credentials.", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this.context, "Please enter valid email", Toast.LENGTH_SHORT).show()
            } else {
                if (!checkPassword(password)) {
                    Toast.makeText(this.context, "Please enter valid password", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (password.compareTo(cPassword) == 0) {
                        val userDetails =
                            UserDetails(name, email, password, cPassword, age, phone, bio)
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this.context,
                                        "Sign up successful.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this.context,
                                        "Sign up failed.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            this.context,
                            "Passwords do not match.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun checkPassword(password: String): Boolean {
        val pattern: Pattern
        val pPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(pPattern)
        val matcher: Matcher = pattern.matcher(password)

        return matcher.matches()
    }
}

/*class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var auth: FirebaseAuth
    private lateinit var rootNode: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.getReference("Users")
        auth = FirebaseAuth.getInstance()

        view.findViewById<Button>(R.id.up015).setOnClickListener {
            val name: String =
                view.findViewById<AppCompatEditText>(R.id.up0004).text.toString().trim()
            val email: String =
                view.findViewById<AppCompatEditText>(R.id.up004).text.toString().trim()
            val password: String =
                view.findViewById<AppCompatEditText>(R.id.up006).text.toString().trim()
            val cPassword: String =
                view.findViewById<AppCompatEditText>(R.id.up008).text.toString().trim()
            val age: String = view.findViewById<AppCompatEditText>(R.id.up01).text.toString().trim()
            val phone: String =
                view.findViewById<AppCompatEditText>(R.id.up012).text.toString().trim()
            val bio: String =
                view.findViewById<AppCompatEditText>(R.id.up014).text.toString().trim()
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
                                            Toast.makeText(
                                                this.context,
                                                "Account created.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Navigation.findNavController(view)
                                                .navigate(R.id.action_signUpFragment_to_signInFragment)
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

        view.findViewById<TextView>(R.id.to_sign_in).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun validatePass(pass: String): Boolean {
        var regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,20}$"
        var p: Pattern = Pattern.compile(regex)
        if (pass.isEmpty()) return false
        var m = p.matcher(pass)
        return m.matches()
    }
}*/