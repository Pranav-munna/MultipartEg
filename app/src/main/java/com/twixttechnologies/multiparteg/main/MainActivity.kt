package com.twixttechnologies.multiparteg.main

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.twixttechnologies.multiparteg.R
import com.twixttechnologies.multiparteg.fragment.FragmentMain

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState==null) {

            supportFragmentManager.beginTransaction()
                    .add(R.id.main, FragmentMain(), MainActivity::class.java.name)
                    .commit()
        }
    }
}
