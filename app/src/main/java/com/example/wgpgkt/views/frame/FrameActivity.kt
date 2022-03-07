package com.example.wgpgkt.views.frame

import android.os.Bundle
import com.example.wgpgkt.R
import com.example.wgpgkt.base.BaseActivity
import com.example.wgpgkt.databinding.ActivityFrameBinding

class FrameActivity: BaseActivity<ActivityFrameBinding>() {
    override val layoutResID: Int = R.layout.activity_frame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //btnClick()
    }

    override fun btnClick() {
        binding.btnTest1.setOnClickListener {
            makeToast("BUTTON1")
        }
        binding.btnTest2.setOnClickListener {
            makeToast("BUTTON2")
        }

    }
}