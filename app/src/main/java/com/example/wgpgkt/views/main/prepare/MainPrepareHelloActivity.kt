package com.example.wgpgkt.views.main.prepare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wgpgkt.databinding.ActivityMainPrepareHelloBinding
import com.example.wgpgkt.views.frame.FrameActivity

class MainPrepareHelloActivity:AppCompatActivity() {
    private lateinit var _binding: ActivityMainPrepareHelloBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainPrepareHelloBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //supportActionBar?.hide()


        val pref = getSharedPreferences("PREF",0)
        binding.tvMainPrepareHello.text = getHelloString(pref.getString("NAME","")!!)

        binding.btnMainPrepareHelloNext.setOnClickListener {
            val intent = Intent(this, FrameActivity::class.java)
            startActivity(intent)
        }
    }

    fun getHelloString(str:String):String{
        return str + "님\n만나서 반가워요:)"
    }
}