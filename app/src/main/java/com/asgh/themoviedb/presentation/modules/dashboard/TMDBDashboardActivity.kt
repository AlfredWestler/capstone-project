package com.asgh.themoviedb.presentation.modules.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.asgh.themoviedb.R
import com.asgh.themoviedb.databinding.TmdbDashboardActivityBinding
import com.asgh.themoviedb.presentation.modules.login.TMDBLoginActivity
import com.firebase.ui.auth.AuthUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TMDBDashboardActivity : AppCompatActivity() {

    private lateinit var binding: TmdbDashboardActivityBinding
    private val vm: TMDBDashboardRxViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TmdbDashboardActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment? ?: return
        val navController = host.navController

        binding.bottomNavigation.setupWithNavController(navController)

        binding.moreBtn.setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnCompleteListener {
                startActivity(Intent(this, TMDBLoginActivity::class.java))
                finish()
            }
        }

        vm.toolbarTitle.observe(this) {
            binding.toolbarTitle.text = it
        }
    }
}