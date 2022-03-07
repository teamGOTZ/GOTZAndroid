package com.example.wgpgkt.views.main.prepare

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.wgpgkt.R
import com.example.wgpgkt.base.BaseActivity
import com.example.wgpgkt.databinding.ActivityMainPrepareIntroBinding
import com.example.wgpgkt.views.main.MainViewModel

class MainPrepareIntroActivity : BaseActivity<ActivityMainPrepareIntroBinding>() {

    override val layoutResID: Int = R.layout.activity_main_prepare_intro

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()

        binding.viewmodel = viewModel

        btnClick()
    }

    override fun btnClick(){
        binding.btnMainPrepareIntroNext.setOnClickListener {
            val intent = Intent(this, MainPrepareNameActivity::class.java)
            startActivity(intent)
        }
        /*
        binding.btnMainLogin.setOnClickListener {

            makeToast("MainActivity :: LOGIN")
        }

        binding.btnMainJoin.setOnClickListener {
            val intent = Intent(this, MainJoinActivity::class.java)
            startActivity(intent)
        }

        binding.tvMainFind.setOnClickListener {
            try{
                val intent = Intent(this, MainFindActivity::class.java)
                startActivity(intent)
            }catch (e:Exception){
                Log.e("MainActivity : ", e.message.toString() )
            }
        }*/
    }
}