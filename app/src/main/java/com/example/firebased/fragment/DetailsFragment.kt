package com.example.firebased.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.firebased.R
import com.example.firebased.databinding.FragmentDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        view.findViewById<ImageView>(R.id.ls013).setOnClickListener {
            auth.signOut()
            Navigation.findNavController(view).navigate(R.id.action_detailsFragment_to_newsFragment)
        }
    }
}

/*
class DetailsFragment : Fragment(*/
/*R.layout.fragment_details*//*
) {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var rootnode: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        */
/*val toolbar = binding.ls001
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)*//*

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootnode = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        user = FirebaseAuth.getInstance().currentUser!!
        val email = user.email!!
        reference = rootnode.getReference("Users").child(email)

        reference.get().addOnSuccessListener {
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

            Log.d("snapshot", it.value.toString())
        }

        binding.ls013.setOnClickListener {
            auth.signOut()
            Navigation.findNavController(view)
                .navigate(R.id.action_detailsFragment_to_signInFragment)
        }
    }
}*/
