package com.example.greenroad.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.greenroad.R
import com.example.greenroad.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.bottomNavigationView.background = null
        checkPermission(android.Manifest.permission.ACTIVITY_RECOGNITION)

        //Navigation with Menu
        val navController = findNavController(R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(
            _binding.bottomNavigationView,
            navController = navController
        )


    }

    private fun checkPermission(permission: String) {

        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                println("Granted")
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACTIVITY_RECOGNITION) -> {
                println("Denied")

                showInContextUI(permission)
            }

            else -> {
                requestPermissions(arrayOf(permission), 500)
            }
        }

    }

    private fun showInContextUI(permission: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Permission Required")
        dialogBuilder.setMessage("This feature requires the requested permission to count steps properly")
        dialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            requestPermissions(arrayOf(permission), 500)
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            //normal devam et
        }
        dialogBuilder.create().show()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 500) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Physical Activity Permission Granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this@MainActivity, "Physical Activity Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


}