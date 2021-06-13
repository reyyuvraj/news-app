package com.example.firebased

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import com.example.firebased.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.about->{
                //navController.navigate(R.id.action_newsFragment_to_aboutFragment)
                Toast.makeText(requireActivity(), "FIX THIS!!!", Toast.LENGTH_SHORT).show()
            }
            R.id.licenses->{
                //startActivity(Intent(this, OssLicensesMenuActivity::class.java))
                Toast.makeText(requireActivity(), "Licenses having an issue!!!", Toast.LENGTH_SHORT).show()
            }
            R.id.details->{
                //navController.navigate(R.id.action_newsFragment_to_detailsFragment)
                Toast.makeText(requireActivity(), "FIX THIS!!!", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}