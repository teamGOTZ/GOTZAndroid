package com.example.wgpgkt.views.main.prepare

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.wgpgkt.databinding.ActivityMainPrepareNameBinding

class MainPrepareNameActivity: AppCompatActivity() {
    private lateinit var _binding: ActivityMainPrepareNameBinding
    private val binding get() = _binding

    private val viewModel: MainPrepareNameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainPrepareNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //supportActionBar?.hide()

        binding.viewmodel = viewModel

        viewModel.name.observe(this, Observer {
            if(it.length == 0) binding.btnMainPrepareNameNext.isEnabled = false
            else binding.btnMainPrepareNameNext.isEnabled = true
            Log.e("Observer", it.length.toString())
        })

        binding.btnMainPrepareNameNext.setOnClickListener {
            val pref = getSharedPreferences("PREF", 0)
            val edit = pref.edit()
            edit.putString("NAME", binding.etMainPrepareName.text.toString())
            edit.apply()

            val intent = Intent(this, MainPrepareHelloActivity::class.java)
            startActivity(intent)
        }

    }


}