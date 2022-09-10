package com.example.firebased.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import com.example.firebased.R
import com.example.firebased.databinding.FragmentDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val uId = auth.currentUser?.uid
        val id = 0

        db.collection("$uId")
            .get().addOnSuccessListener { result ->
                binding.lsBar.visibility = ProgressBar.GONE

                var name: String = "name"
                var email: String = "email"
                var age: String = "age"
                var phone: String = "phone"
                var bio: String = "bio"

                for (document in result) {
                    val doc = document.data
                    Log.d("fire", "onViewCreated: ${document.id} => ${document.data}")
                    name = doc["name"].toString()
                    email = doc["email"].toString()
                    age = doc["age"].toString()
                    phone = doc["phone"].toString()
                    bio = doc["bio"].toString()
                }
                val res = result.metadata
                Log.d("store", "onViewCreated: $res")

                binding.ls004.text = name
                binding.ls006.text = email
                binding.ls008.text = age
                binding.ls01.text = phone
                binding.ls012.text = bio

            }.addOnFailureListener { exception ->
                Log.d("fetch", "onViewCreated: Error getting details: $exception")
            }

        binding.ls013.setOnClickListener {
            auth.signOut()
            Navigation.findNavController(view)
                .navigate(R.id.action_detailsFragment_to_signInFragment)
        }
    }
}