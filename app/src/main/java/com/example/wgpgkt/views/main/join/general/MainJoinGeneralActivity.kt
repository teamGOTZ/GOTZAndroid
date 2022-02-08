package com.example.wgpgkt.views.main.join.general

import android.os.Bundle
import androidx.activity.viewModels
import com.example.wgpgkt.R
import com.example.wgpgkt.base.BaseActivity
import com.example.wgpgkt.databinding.ActivityMainJoinGeneralBinding

class MainJoinGeneralActivity: BaseActivity<ActivityMainJoinGeneralBinding>() {
    override val layoutResID: Int = R.layout.activity_main_join_general

    private val viewModel: MainJoinGeneralViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewmodel = viewModel
    }
}