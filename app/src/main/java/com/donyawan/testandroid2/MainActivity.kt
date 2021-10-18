package com.donyawan.testandroid2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.donyawan.testandroid2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, AddTask())
            .commit()
    }
}