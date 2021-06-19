package com.example.firebased.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.firebased.R
import com.example.firebased.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

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
        binding = FragmentAboutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ut005.setOnClickListener {
            val uri = Uri.parse("https://github.com/reyyuvraj/Firebased")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            requireActivity().startActivity(intent)
        }

        binding.toDocument.setOnClickListener {
            val uri = Uri.parse("https://docs.google.com/document/d/184YmELP5jQmvMcNuLG0alQLtbgToeiqJKlJ3UEjJgHI/edit")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            requireActivity().startActivity(intent)
        }
    }
}