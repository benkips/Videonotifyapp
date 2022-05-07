package com.mabnets.videouploaderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mabnets.videouploaderapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import java.io.File
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController=navHostFragment.findNavController()

       setSupportActionBar(binding.toolbar)
        binding.bnav.setupWithNavController(navController)
        //setupActionBarWithNavController(navController)
    }
}