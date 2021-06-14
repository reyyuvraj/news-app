package com.example.firebased.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.firebased.R
import com.example.firebased.databinding.ActivityMainBinding
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                navController.navigate(R.id.action_newsFragment_to_aboutFragment)
                //Toast.makeText(requireActivity(), "FIX THIS!!!", Toast.LENGTH_SHORT).show()
            }
            R.id.licenses -> {
                startActivity(Intent(this, OssLicensesMenuActivity::class.java))
                //Toast.makeText(this, "Licenses having an issue!!!", Toast.LENGTH_SHORT).show()
            }
            R.id.details -> {
                navController.navigate(R.id.action_newsFragment_to_detailsFragment)
                //Toast.makeText(requireActivity(), "FIX THIS!!!", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}