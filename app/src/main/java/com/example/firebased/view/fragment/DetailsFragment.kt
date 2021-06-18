package com.example.firebased.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.firebased.R
import com.example.firebased.databinding.FragmentDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var rootNode: FirebaseDatabase
    private lateinit var reference: DatabaseReference

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
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        rootNode =
            FirebaseDatabase.getInstance("https://fir-d-db270-default-rtdb.asia-southeast1.firebasedatabase.app")
        user = FirebaseAuth.getInstance().currentUser!!
        val uID = auth.currentUser!!.uid
        reference = rootNode.getReference("users").child(uID)

        reference.get().addOnSuccessListener {
            binding.lsBar.visibility = ProgressBar.GONE
            var name: String = it.child("name").value.toString()
            var email: String = it.child("email").value.toString()
            var age: String = it.child("age").value.toString()
            var phone: String = it.child("phone").value.toString()
            var bio: String = it.child("bio").value.toString()

            binding.ls004.text = name
            binding.ls006.text = email
            binding.ls008.text = age
            binding.ls01.text = phone
            binding.ls012.text = bio
        }

        binding.ls013.setOnClickListener {
            auth.signOut()
            Navigation.findNavController(view)
                .navigate(R.id.action_detailsFragment_to_signInFragment)
        }
    }
}